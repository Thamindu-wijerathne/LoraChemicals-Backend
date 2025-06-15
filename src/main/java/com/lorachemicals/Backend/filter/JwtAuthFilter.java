package com.lorachemicals.Backend.filter;

import com.lorachemicals.Backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthFilter extends HttpFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        logger.info("Filtering request: {} {}", method, path);

        if (path.equals("/users/login")) {
            logger.info("Skipping JWT check for login endpoint");
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization header: {}", authHeader);

        // ADD THIS DEBUG LINE
        logger.info("Token validation result: {}", JwtUtil.isTokenValid(authHeader));

        if (!JwtUtil.isTokenValid(authHeader)) {
            logger.warn("Invalid or missing token for path: {}", path);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing token");
            return;
        }

        logger.info("Token is valid, proceeding with request");
        chain.doFilter(request, response);
    }
}