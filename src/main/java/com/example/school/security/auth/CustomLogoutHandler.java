package com.example.school.security.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.example.school.security.token.ITokenRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler{

    @Autowired
    private ITokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Get Token From Request
        String tokenFromCookie = Arrays
            .stream(request.getCookies())
            .filter(cookie -> "accessToken".equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElse(null);

            var token = tokenRepository.findByToken(tokenFromCookie).orElseThrow();
            if (token != null) {
                token.setExpired(true);
                token.setRevoked(true);
                tokenRepository.save(token);
            }

            ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true) // To prevent XSS Attack
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
    
}
