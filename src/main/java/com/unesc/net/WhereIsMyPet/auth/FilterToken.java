package com.unesc.net.WhereIsMyPet.auth;

import com.unesc.net.WhereIsMyPet.entity.user.Usuario;
import com.unesc.net.WhereIsMyPet.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization != null) {
            authorization = authorization.replace("Bearer ", "");

            String subject = this.tokenService.getSubject(authorization);
            Optional<Usuario> userByEmail = this.userRepository.findUserByEmail(subject);
            if (userByEmail.isPresent()) {
                Usuario usuario = userByEmail.get();
                SecurityContextHolder.getContext().setAuthentication(usuario);
            }
        }
        filterChain.doFilter(request, response);
    }
}
