package com.project.e_library.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println(request.getHeader("Authorization"));

        String token = extractToken(request);

        if(token != null && jwtService.validateToken(token)) {
            String email = jwtService.getEmailFromToken(token);
            System.out.println(email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                    email, null, Collections.emptyList());

            authentication.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(SecurityContextHolder.getContext().getAuthentication());
        }

        filterChain.doFilter(request, response);

    }

    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.equals("/api/login")
                || path.equals("/api/register")
                || path.equals("/api/logout")
                || path.equals("/public/**");
    }


    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
