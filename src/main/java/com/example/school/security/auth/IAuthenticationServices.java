package com.example.school.security.auth;

import com.example.school.security.token.TokenInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthenticationServices {
    
    public AuthenticationResponse register(RegisterRequest request,HttpServletResponse response) throws Exception;

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) throws Exception;

    public TokenInfo getTokenInfo(HttpServletRequest request) throws Exception;

    public String confirmToken(HttpServletRequest reques,String code) throws Exception;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // public String getVerifCode(String username);

    public String verfiy(String type, String toEmail);

}
