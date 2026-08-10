package com.nypunya.course.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String secret;
    public JwtAuthenticationFilter(@Value("${security.jwt.secret}") String secret) { this.secret = secret; }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                Claims claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                        .parseClaimsJws(header.substring(7)).getBody();
                String role = claims.get("role", String.class);
                String subject = claims.getSubject();
                if (role != null && subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            subject, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))));
                }
            } catch (RuntimeException ignored) { }
        }
        chain.doFilter(request, response);
    }
}
