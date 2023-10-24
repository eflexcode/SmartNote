package com.larex.SmartNote.filter;

import com.larex.SmartNote.service.UserService;
import com.larex.SmartNote.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

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
         String userEmail = null;
         String jwt = null;

        if (header != null && header.startsWith("Bearer ")){
            jwt = header.substring(7);
            log.debug("JWT {}",jwt);
            try {
                userEmail = jwtUtils.extractEmail(jwt);
            }catch (IllegalArgumentException exception){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unable to get JWT token");

            }catch (ExpiredJwtException jwtException){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"JWT token expired");
            }
        }


        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userService.userDetailService().loadUserByUsername(userEmail);

            if (jwtUtils.validateToken(jwt,userDetails)){

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request,response);

    }
}
