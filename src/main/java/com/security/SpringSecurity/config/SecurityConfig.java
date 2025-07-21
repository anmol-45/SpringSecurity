package com.security.SpringSecurity.config;


import com.security.SpringSecurity.filters.CustomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomFilter customFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity auth) throws Exception{

        return auth
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->
                        request.requestMatchers("/api/v1/public/**").permitAll()
                                .anyRequest()
                                .authenticated())
                .addFilterBefore(customFilter , UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
