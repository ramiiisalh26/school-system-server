package com.example.school.security.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;

import static com.example.school.user.Permission.ADMIN_CREATE;
import static com.example.school.user.Permission.ADMIN_DELETE;
import static com.example.school.user.Permission.ADMIN_READ;
import static com.example.school.user.Permission.ADMIN_UPDATE;
import static com.example.school.user.Permission.PARENT_CREATE;
import static com.example.school.user.Permission.PARENT_READ;
import static com.example.school.user.Permission.PARENT_DELETE;
import static com.example.school.user.Permission.PARENT_UPDATE;

import static com.example.school.user.Permission.STUDENT_CREATE;
import static com.example.school.user.Permission.STUDENT_DELETE;
import static com.example.school.user.Permission.STUDENT_READ;
import static com.example.school.user.Permission.STUDENT_UPDATE;

import static com.example.school.user.Permission.TEACHER_CREATE;
import static com.example.school.user.Permission.TEACHER_READ;
import static com.example.school.user.Permission.TEACHER_DELETE;
import static com.example.school.user.Permission.TEACHER_UPDATE;
import static com.example.school.user.Role.ADMIN;
import static com.example.school.user.Role.PARENT;
import static com.example.school.user.Role.STUDENT;
import static com.example.school.user.Role.TEACHER;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
        "/api/v1/payment/**",
        "/api/v1/teacher/**",
        "/api/v1/student/**",
        "/api/v1/assignment/**",
        "/api/v1/calendar_events/**",
        "/api/v1/announcements/**",
        "/api/v1/classes/**",
        "/api/v1/events/**",
        "/api/v1/exam/**",
        "/api/v1/lesson/**",
        "/api/v1/parent/**",
        "/api/v1/result/**",
        "/api/v1/courses/**",
        "/api/v1/auth/**",
        "/v2/api-docs",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui/**",
        "/webjars/**",
        "/swagger-ui.html"
    };

    private final JwtConfig jwtConfig;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    
    @Qualifier("CustomLogoutHandler")
    private final LogoutHandler logoutHandler; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                req.requestMatchers(WHITE_LIST_URL)
                .permitAll()
                .requestMatchers(POST,"/api/v1/auth/login").permitAll()
                .requestMatchers(POST,"/api/v1/auth/register").permitAll()
                .requestMatchers(POST,"/api/v1/auth/reset-password").permitAll()
                .requestMatchers(POST,"/api/v1/auth/redeem-password").permitAll()
                .requestMatchers("/api/v1/dashboard/admin/**").hasAnyRole(ADMIN.name())
                .requestMatchers("/api/v1/dashboard/teacher/**").hasAnyRole(TEACHER.name())
                .requestMatchers(GET,"/api/v1/dashboard/teacher/**").hasAnyAuthority(TEACHER_READ.name(),ADMIN_READ.name())
                .requestMatchers(POST,"/api/v1/dashboard/teacher/**").hasAnyAuthority(TEACHER_CREATE.name(),ADMIN_CREATE.name())
                .requestMatchers(PUT,"/api/v1/dashboard/teacher/**").hasAnyAuthority(TEACHER_UPDATE.name(),ADMIN_UPDATE.name())
                .requestMatchers(DELETE,"/api/v1/dashboard/teacher/**").hasAnyAuthority(ADMIN_DELETE.name(),TEACHER_DELETE.name())
                .requestMatchers("/api/v1/dashboard/parent/**").hasAnyRole(PARENT.name(),TEACHER.name(),ADMIN.name())
                .requestMatchers(GET,"/api/v1/dashboard/parent/**").hasAnyAuthority(PARENT_READ.name(),TEACHER_READ.name(),ADMIN_READ.name())
                .requestMatchers(POST,"/api/v1/dashboard/parent/**").hasAnyAuthority(PARENT_CREATE.name(),ADMIN_CREATE.name())
                .requestMatchers(PUT,"/api/v1/dashboard/parent/**").hasAnyAuthority(PARENT_UPDATE.name(),ADMIN_UPDATE.name())
                .requestMatchers(DELETE,"/api/v1/dashboard/parent/**").hasAnyAuthority(PARENT_DELETE.name(),ADMIN_DELETE.name())
                .requestMatchers("/api/v1/dashboard/student/**").hasAnyRole(STUDENT.name(),ADMIN.name(),PARENT.name(),TEACHER.name())
                .requestMatchers(GET,"/api/v1/dashboard/student/**").hasAnyAuthority(STUDENT_READ.name(),TEACHER_READ.name(),PARENT_READ.name(),ADMIN_READ.name())
                .requestMatchers(POST,"/api/v1/dashboard/student/**").hasAnyAuthority(STUDENT_CREATE.name(),TEACHER_CREATE.name(),PARENT_CREATE.name(),ADMIN_CREATE.name())
                .requestMatchers(PUT,"/api/v1/dashboard/student/**").hasAnyAuthority(STUDENT_UPDATE.name(),PARENT_UPDATE.name(),ADMIN_UPDATE.name(),TEACHER_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/dashboard/student/**").hasAnyAuthority(STUDENT_DELETE.name(),TEACHER_DELETE.name(),ADMIN_DELETE.name())
                .anyRequest()
                .authenticated()   
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .oauth2ResourceServer(config -> config.jwt(jwt -> {
                try {
                    jwt.decoder(jwtConfig.jwtDecoder());
                } catch (Exception e) {
                    throw new IllegalStateException();
                }
            }))
            .logout(logout -> 
                logout.logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())          
            );
            return http.build();
    }

}
