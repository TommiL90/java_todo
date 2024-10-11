package com.timix.todo.back.filter;

import java.io.IOException;
import com.timix.todo.back.modules.user.entity.UserEntity;
import com.timix.todo.back.modules.user.repository.UserRepository;
import com.timix.todo.back.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class FilterAuth extends OncePerRequestFilter {

    private static final String HEADER_PREFIX = "Bearer ";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (servletPath.equals("/users/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (servletPath.startsWith("/todo") || servletPath.startsWith("/users")) {
            String authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith(HEADER_PREFIX)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
            }

            try {
                String token = resolveToken(request);

                if (token != null && jwtProvider.validateToken(token)) {
                    var auth = jwtProvider.getAuthentication(token);
                    if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }

                String username = jwtProvider.getAuthentication(token).getName();
                var userOptional = userRepository.findByUsername(username);

                if (userOptional.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                    return;
                }

                UserEntity user = userOptional.get();
                request.setAttribute("idUser", user.getId());
                filterChain.doFilter(request, response);
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to decode authentication credentials");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(HEADER_PREFIX.length());
        }
        return null;
    }
}