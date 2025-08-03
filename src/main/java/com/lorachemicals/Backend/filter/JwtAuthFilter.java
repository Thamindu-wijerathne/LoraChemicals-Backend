package com.lorachemicals.Backend.filter;

import com.lorachemicals.Backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
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

        logger.info("=== Filtering request: {} {} ===", method, path);

        // Log all headers for debugging
        logger.info("All request headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            logger.info("  {}: {}", headerName, headerValue);
        }

        // Skip JWT check for login endpoint and OPTIONS requests
        if (path.equals("/users/login") || path.startsWith("/getid/") || path.equals("/forgot/forgot-password") || method.equals("OPTIONS")) {
            logger.info("Skipping JWT check for {} endpoint", path);
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization header: {}", authHeader);

        // Check if token is valid
        boolean isValid = JwtUtil.isTokenValid(authHeader);
        logger.info("Token validation result: {}", isValid);

        if (!isValid) {
            logger.warn("Invalid or missing token for path: {}", path);

            // Set CORS headers before sending error response
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid or missing token\"}");
            return;
        }

        logger.info("Token is valid, proceeding with request");
        chain.doFilter(request, response);
    }
}