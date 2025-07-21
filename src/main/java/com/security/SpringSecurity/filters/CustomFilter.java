package com.security.SpringSecurity.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class CustomFilter extends OncePerRequestFilter {

    @Value("${spring.security.token}")
    String token;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        //match the token and then check the authorization header and role

        String path = request.getRequestURI();
        if(path.contains("public")) {
            filterChain.doFilter(request, response);
            return;
        }

        String role = request.getHeader("X-ROLE");
        String authToken = request.getHeader("Authorization");

        if(authToken != null && authToken.equals(token) && role.equalsIgnoreCase("ADMIN")){
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    "customUser",null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role)
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request , response);

        }
        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("role mismatched or unauthorized User");

        }




    }
}
