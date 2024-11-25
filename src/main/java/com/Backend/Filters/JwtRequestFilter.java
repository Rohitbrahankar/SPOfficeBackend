package com.Backend.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Backend.Entities.Admin;
import com.Backend.Entities.Subadmin;
import com.Backend.Service.AdminService;
import com.Backend.Service.SubadminService;
import com.Backend.Utils.JwtUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AdminService adminService;
    private final SubadminService subadminService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(@Lazy AdminService adminService, JwtUtil jwtUtil, SubadminService subadminService) {
        this.adminService = adminService;
        this.subadminService = subadminService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Admin> adminOptional = adminService.getAdminByUsername(username);
            if (adminOptional.isPresent()) {
                Admin admin = adminOptional.get();
                UserDetails userDetails = new User(admin.getUsername(), admin.getPassword(), Collections.emptyList());

                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } else {
                Optional<Subadmin> subadminOptional = subadminService.getSubdminByUsername(username);
                if (!subadminOptional.isPresent())
                    return;
                Subadmin subadmin = subadminOptional.get();
                UserDetails userDetails = new User(subadmin.getUsername(), subadmin.getPassword(),
                        Collections.emptyList());

                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }

        filterChain.doFilter(request, response);

    }
}
