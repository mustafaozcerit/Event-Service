package com.opthema.hcm.java_microservice.application.security;

import com.opthema.hcm.java_microservice.application.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Header'dan JWT token al
            String token = extractToken(request);
            if (token != null && jwtService.validateToken(token)) {
                // Kullanıcı adını token'dan çıkar
                String username = jwtService.extractUsername(token);

                // Kullanıcı detaylarını oluştur (DB sorgusu yok, sadece basit bir UserDetails nesnesi)
                UserDetails userDetails = new User(username, "", Collections.emptyList());

                // Kimlik doğrulama nesnesini oluştur ve SecurityContext'e ata
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            // Token süresi dolmuşsa, 401 Unauthorized yanıtı döndür
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 Unauthorized
            response.getWriter().write("Token has expired: " + e.getMessage());
            return;  // Daha fazla işlem yapma
        } catch (Exception e) {
            // Diğer hatalar için genel bir mesaj
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
            response.getWriter().write("JWT validation error: " + e.getMessage());
            return;  // Daha fazla işlem yapma
        }

        // Eğer token geçerliyse, filtreyi işlemi devam ettir
        filterChain.doFilter(request, response);
    }


    // Bearer token'ı Authorization header'ından çıkaran metod
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}