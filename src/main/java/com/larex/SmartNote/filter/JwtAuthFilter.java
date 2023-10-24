package com.larex.SmartNote.filter;

import com.larex.SmartNote.service.UserService;
import com.larex.SmartNote.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String header = request.getHeader("Authorization");
        final String userEmail;
        final String jwt;

        if (header.isEmpty() || header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = header.substring(7);
        log.debug("JWT {}",jwt);
        userEmail = jwtUtils.extractEmail(jwt);

        if (!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userService.userDetailService().loadUserByUsername(userEmail);

        }

    }
}
