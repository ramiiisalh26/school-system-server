package com.example.school.security.config;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Autowired
    private JwtConfig jwtConfig;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) throws Exception{
        return extractClaim(token, claims -> (String) claims.get("sub"));
    }

    public <T> T extractClaim(String token, Function<Map<String, Object>,T> claimsResolver) throws Exception{
        final Map<String, Object> claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) throws Exception{
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) throws Exception{
        extractClaims.put("role",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return buildToken(extractClaims,userDetails,jwtExpiration);
    }

    public boolean isTokenVaild(String token, UserDetails userDetails) throws Exception{
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String generateRefreshToken(UserDetails userDetails) throws Exception{
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        // extraClaims.put("confirm_At", extraClaims)
        return buildToken(extraClaims, userDetails, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration)throws Exception{
        
        var now = Instant.now();

        var claims = JwtClaimsSet.builder()
                .issuer("login_app")
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiration))
                .claim("extraClaims", extraClaims)
                .build();

            return jwtConfig.jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    
    private Map<String, Object> extractAllClaims(String token) throws Exception{
        JwtDecoder jwtDecoder = jwtConfig.jwtDecoder();
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaims();
    }

    private boolean isTokenExpired(String token) throws Exception{
        return extractExpiration(token).isBefore(Instant.now());
    }

    private Instant extractExpiration(String token) throws Exception{
        return extractClaim(token, claims -> (Instant) claims.get("exp"));
    }
}
