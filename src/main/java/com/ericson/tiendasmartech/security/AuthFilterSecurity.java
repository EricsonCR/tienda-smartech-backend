package com.ericson.tiendasmartech.security;

import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFilterSecurity extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                String username = jwtService.getUsernameToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (!jwtService.expiredToken(token)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(username, null, null
                                );
                        authenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            responseException(response, "JWT_EXPIRED");
            return;
        } catch (JwtException e) {
            responseException(response, "JWT_INVALID");
            return;
        } catch (AuthenticationServiceException e) {
            responseException(response, "AUTH_ERROR");
            return;
        } catch (Exception e) {
            responseException(response, "EXCEPTION");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void responseException(HttpServletResponse response, String exception) throws IOException {
        String message;
        switch (exception) {
            case "JWT_EXPIRED" -> message = "Expired JWT";
            case "JWT_INVALID" -> message = "Invalid JWT";
            case "AUTH_ERROR" -> message = "AUTH Error";
            case "EXCEPTION" -> message = "Exception";
            default -> message = "Unknown exception";
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ControllerResponse controllerResponse = new ControllerResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        response.getWriter().write(new ObjectMapper().writeValueAsString(controllerResponse));
    }
}