package com.ra.security.config;

import com.ra.security.jwt.CustomAccessDeniedHandler;
import com.ra.security.jwt.JwtEntryPoint;
import com.ra.security.jwt.JwtTokenFilter;
import com.ra.security.user_principal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailService userDetailService;
   @Autowired
   private JwtTokenFilter jwtTokenFilter;
   @Autowired
   private JwtEntryPoint jwtEntryPoint;
   @Autowired
   private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.
                cors(auh->auh.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:5500/"));
                    corsConfiguration.setAllowedMethods(List.of("*"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    return corsConfiguration;
                })).
                csrf(AbstractHttpConfigurer::disable).
                authenticationProvider(authenticationProvider()).
                authorizeHttpRequests((auth)->
                        auth.requestMatchers("/api/v1/auth/**").permitAll().
                                requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN").
                                requestMatchers("/api/v1/user/**").hasAuthority("USER").
                                anyRequest().authenticated()).
                exceptionHandling(auth->
                        auth.authenticationEntryPoint(jwtEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)).
                sessionManagement((auth)->auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
