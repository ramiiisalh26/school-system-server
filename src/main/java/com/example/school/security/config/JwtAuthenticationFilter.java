package com.example.school.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.school.security.token.ITokenRepository;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final ITokenRepository tokenRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionResolver;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService
        ,ITokenRepository tokenRepository, HandlerExceptionResolver exceptionResolver){
            this.jwtService = jwtService;
            this.userDetailsService = userDetailsService;
            this.tokenRepository = tokenRepository;
            this.exceptionResolver = exceptionResolver;
    }

    public JwtAuthenticationFilter(){
        this.jwtService = null;
        this.userDetailsService = null;
        this.tokenRepository = null;
        this.exceptionResolver = null;
    }

    public JwtAuthenticationFilter(HandlerExceptionResolver exceptionResolver){
        this(null, null, null, exceptionResolver);
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain) throws ServletException, IOException {
            String username = null;
            String jwt = null;

            if (request.getCookies() != null) {
                for(Cookie cookie: request.getCookies()){
                    if (cookie.getName().equals("refreshToken")) {
                        jwt = cookie.getValue();
                    }
                }
            }

            if (jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                username = jwtService.extractUsername(jwt);
                System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println("KKKKKKKKKKKKKKKKKK");
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // validation from database
                    boolean isTokenVaild = tokenRepository.findByToken(jwt)
                        .map(t->!t.isExpired() && !t.isRevoked())
                        .orElse(false);

                    if (jwtService.isTokenVaild(jwt, userDetails) && isTokenVaild) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                exceptionResolver.resolveException(request, response, null, e);
            }
        filterChain.doFilter(request, response);
    }

}
