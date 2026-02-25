package fr.k4cook.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.k4cook.configs.SecurityConfig;
import fr.k4cook.services.JwtService;
import fr.k4cook.services.UserDetailsServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Jwt Authentication Filter
 * Extends OncerPerRequestFilter
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    /**
     * Autowired JwtService
     */
    @Autowired
    private JwtService jwtService;

    /**
     * Autowired UserDetailsService Implementation
     */
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void testPassword() {
        String hash = "$2a$10$Dow1RzYpQeWb9mC3n5Jk6eUuO2eH3u7l8YxVtWqZ1oR9pLmN0aBc";
        System.out.println(passwordEncoder.matches("admin123", hash));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        boolean isIgnoredGetPath = SecurityConfig.ignoredGetPaths.stream().anyMatch(path -> path.matches(request))
                && request.getMethod().equals("GET");

        boolean isIgnoredPostPath = SecurityConfig.ignoredPostPaths.stream().anyMatch(path -> path.matches(request))
                && request.getMethod().equals("POST");

        if (isIgnoredGetPath || isIgnoredPostPath) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
