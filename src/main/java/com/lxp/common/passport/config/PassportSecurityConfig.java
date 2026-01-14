package com.lxp.common.passport.config;

import com.lxp.common.passport.filter.PassportAuthenticationEntryPoint;
import com.lxp.common.passport.filter.PassportAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class PassportSecurityConfig {

    private final PassportAuthenticationFilter passportAuthenticationFilter;
    private final PassportAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/health", "/actuator/**").permitAll()  // 헬스 체크는 인증 제외
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()  // 이 외의 공개 api
                        .requestMatchers("/internal/api-v1").permitAll()  // 이 외의 internal api
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Passport 필터를 Spring Security 필터 체인에 추가
                .addFilterBefore(
                        passportAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
