package com.example.school.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.school.security.token.TokenInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final IAuthenticationServices authenticationServices;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody final RegisterRequest request
        ,HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(authenticationServices.register(request,response));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody final AuthenticationRequest request
        ,HttpServletResponse response) throws Exception{
        return ResponseEntity.ok(authenticationServices.authenticate(request, response));
    }

    @GetMapping(path = "/tokeInfo")
    public ResponseEntity<TokenInfo> getTokenInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        return ResponseEntity.ok(authenticationServices.getTokenInfo(request));
    }

    @PostMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        authenticationServices.refreshToken(request, response);
    }

    @PostMapping(path = "/verfiy")
    public ResponseEntity<String> verfiy(@RequestParam String type,@RequestParam String toEmail) {
        try {
        
            return ResponseEntity.ok(authenticationServices.verfiy(type, toEmail));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/register/confirm")
    public String confirm(HttpServletRequest request,@RequestParam String code) throws Exception{
        return authenticationServices.confirmToken(request,code);
    }
}
