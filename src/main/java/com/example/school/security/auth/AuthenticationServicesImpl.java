package com.example.school.security.auth;

import java.util.Date;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.school.security.config.JwtService;
import com.example.school.security.notification.INotification;
import com.example.school.security.notification.NotificationFactory;
import com.example.school.security.notification.email.IEmailTester;
import com.example.school.security.token.ITokenServices;
import com.example.school.security.token.Token;
import com.example.school.security.token.TokenInfo;
import com.example.school.security.token.TokenType;
import com.example.school.user.IUserServices;
import com.example.school.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServicesImpl implements IAuthenticationServices{

    private final IUserServices IUserServices;
    private final ITokenServices tokenServices;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IEmailTester emailTester;    
    private final NotificationFactory notificationFactory;
    
    @Override
    public AuthenticationResponse register(RegisterRequest request,HttpServletResponse response) throws Exception {

        boolean isValidEmail = emailTester.test(request.getUsername());
        
        if (!isValidEmail) {
            throw new IllegalStateException("Email not Valid");
        }

        String verifCode = VerificationCodeGenerator.generateVerficationCode();

        User user = User.builder()
            .first_name(request.getFirst_name())
            .last_name(request.getLast_name())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .verifCode(verifCode)
            .build();

        IUserServices.signUp(user);

        String accessToken = jwtService.generateToken(user);        

        ResponseCookie cookie = ResponseCookie.from("refreshToken",accessToken)
                .httpOnly(false) // To Prevent XSS Attack
                .secure(false) // set it true, in production state
                .sameSite("Lax")
                .path("/")
                .maxAge(60000000)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        savedUserToken(user,accessToken);

        return AuthenticationResponse.builder()
            .message("signup is successful")
            .accessToken(accessToken)
            .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) throws Exception{

        if (!IUserServices.isEnabled(request.getEmail())) {
            String verifCode = VerificationCodeGenerator.generateVerficationCode();
            User user = IUserServices.findByEmail(request.getEmail()).orElseThrow();
            user.setVerifCode(verifCode);
            IUserServices.setVerifCode(verifCode,user.getId());
            return AuthenticationResponse.builder().message("Please Verify Email First").build();
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            User user = IUserServices.findByEmail(request.getEmail()).orElseThrow();
            var refreshToken = jwtService.generateRefreshToken(user);

            ResponseCookie cookie = ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(false) // To Prevent XSS Attack
                .secure(false) // set it true, in production state
                .sameSite("Lax")
                .path("/")
                .maxAge(604800000)
                .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            revokeAllUser(user);
            savedUserToken(user,refreshToken);

            return AuthenticationResponse.builder().refreshToken(refreshToken).build();
        }else{
            throw new UsernameNotFoundException("Invaild user request form authentication method");
        }
    }  

    @Override
    @Transactional
    public String confirmToken(HttpServletRequest request,String code) throws Exception{
        
        String token = extractTokenFromCookie(request);  

        Token confirmationToken = tokenServices.getToken(token)
            .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmed_at() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        if (confirmationToken.getExpire_at().isBefore(Instant.now())) {
            throw new IllegalStateException("Token Expired");
        }

        String username = jwtService.extractUsername(confirmationToken.getToken());
        User userVerification = IUserServices.findByEmail(username).orElseThrow();

        if (userVerification != null && userVerification.getVerifCode().equals(code)) {
            tokenServices.setConfirmedAt(token);
            IUserServices.enableUser(confirmationToken.getUser().getUsername());
            return "Confirmed";
        }

        return "Not Confirmed";
    }

    
    private void savedUserToken(User user, String refreshToken) throws Exception{
        // To Get Confiremd value.
        Token gToken = tokenServices.getCofirmedAt(user.getId());
        Instant confiremd =  (gToken != null ? gToken.getConfirmed_at() :  null);
        
        // Build user entity.
        Token token = Token.builder()
            .user(user)
            .created_at(jwtService.extractClaim(refreshToken, claims -> (Instant) claims.get("iat")))
            .expire_at(jwtService.extractClaim(refreshToken, claims -> (Instant) claims.get("exp")))
            .token(refreshToken)
            .tokenType(TokenType.HTTPONLY)
            .confirmed_at(confiremd)
            .role(user.getRole())
            .expired(false)
            .revoked(false)
            .build();

        // Save token of user
        tokenServices.saveToken(token);
    }
    
    private void revokeAllUser(User user) {
        // To get All valid Token of user
        var validUserTokens = tokenServices.findAllValidTokenByUser(user.getId());
        
        if (validUserTokens.isEmpty()) {
            return;
        }
        // set expired and revoked to be true states
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        // save it to database
        tokenServices.saveAll(validUserTokens);
    }

    private String extractTokenFromCookie(HttpServletRequest request){

        String tokenFromCookie = Arrays
                .stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        
        return tokenFromCookie;
    }

    @Override
    public TokenInfo getTokenInfo(HttpServletRequest request) throws Exception{
        String token = extractTokenFromCookie(request);
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = IUserServices.getUser(username);

        if (jwtService.isTokenVaild(token, userDetails)) {
            String role = jwtService.extractClaim(token,claims -> claims.get("role").toString());
            Date expirationDate = jwtService.extractClaim(token, claims -> (Date) claims.get("exp"));
            TokenInfo tokenInfo = TokenInfo.builder()
                .username(username)
                .role(role)
                .expieDate(expirationDate)
                .build();
            return tokenInfo;
        }
        return null;
    }


    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String refreshToken = null;
        final String username;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.IUserServices.findByEmail(username).orElseThrow();
            if (jwtService.isTokenVaild(refreshToken,user)) {
                var accessToken = jwtService.generateRefreshToken(user);
                revokeAllUser(user);
                savedUserToken(user,accessToken);
                ResponseCookie cookie = ResponseCookie.from("refreshToken", accessToken)
                    .httpOnly(true) // To Prevent XSS Attack
                    .secure(true)
                    .path("/")
                    .maxAge(604800000)
                    .build();

                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                var authResponse = AuthenticationResponse.builder().refreshToken(refreshToken).build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public String verfiy(String type, String toEmail){
        String userVerifCode = IUserServices.findByEmail(toEmail).get().getVerifCode();
        System.out.println(userVerifCode);
        if (!userVerifCode.isEmpty()) {
            INotification notification = notificationFactory.createNotification(type);
            notification.sendVerification(toEmail, userVerifCode);
            return "Notification sent successfully!";
        }
        return "User Not Found, Please Sign Up";

    }
}
