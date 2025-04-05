package com.example.airBnb.demo.security;

import com.example.airBnb.demo.entity.User;
import com.example.airBnb.demo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    public JWTAuthFilter(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

         final String requestTokenHeader = request.getHeader("Authorization");
         if(requestTokenHeader==null || !requestTokenHeader.startsWith("Bearer"))
         {
             filterChain.doFilter(request,response);
             return;
         }

         String token = requestTokenHeader.split("Bearer")[1];
         Long userId =jwtService.getUserIdFromToken(token);


         //when user is not logged in
         if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null)
         {
             User user = userService.getUserById(userId);
             //check if the user should be allowed
             UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
             authenticationToken.setDetails(
                     new WebAuthenticationDetailsSource().buildDetails(request)
             );
             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         }
         filterChain.doFilter(request,response);
    }
}
