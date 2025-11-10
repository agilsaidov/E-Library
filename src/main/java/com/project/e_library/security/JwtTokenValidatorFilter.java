package com.project.e_library.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.e_library.dto.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final RedisTemplate<String,String> redisTemplate;

    private static final String TOKEN_BLACKLIST_PREFIX = "blisted_token:";
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);

        try {
            if (token != null) {

                if (Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_BLACKLIST_PREFIX + token))) {
                    sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "BLACK_LISTED_TOKEN", "This token has been revoked");
                    return;
                }

                if (jwtService.validateToken(token)) {
                    String email = jwtService.getEmailFromToken(token);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email, null, Collections.emptyList());

                    authentication.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }

            filterChain.doFilter(request, response);
        }catch (Exception e) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "AUTHENTICATION_ERROR", "Authentication Failed");
        }
    }



    public void sendErrorResponse(HttpServletResponse response,
                                  HttpStatus httpStatus,
                                  String code,
                                  String message) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), code, message , LocalDateTime.now());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
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
