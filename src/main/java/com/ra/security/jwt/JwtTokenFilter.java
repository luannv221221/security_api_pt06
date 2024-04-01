package com.ra.security.jwt;

import com.ra.security.user_principal.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtProvider jwtProvider;
    @Autowired
    private UserDetailService userDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token =getTokenFromRequest(request);

        try {
            if(token != null && jwtProvider.validateToken(token)){
                String userName = jwtProvider.getUserNameFromToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                if(userDetails != null){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
    public String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        //Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTIzNDU2Nzg5IiwiaWF0IjoxNzEwODM
        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
