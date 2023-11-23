package com.example.hogwartsPoints.filter;

import com.example.hogwartsPoints.dto.TokenDataDTO;
import com.example.hogwartsPoints.exception.JwtFilterExecption;
import com.example.hogwartsPoints.respository.UserRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            if (request.getServletPath().startsWith("/auth/login")
                    || request.getServletPath().startsWith("/users/register")) {
                filterChain.doFilter(request, response);
            } else {
                TokenDataDTO validTokenData = jwtUtil.tokenValidator(request.getHeader("Authorization"));
                request.setAttribute("userId", validTokenData.getId());
                request.setAttribute("userName", validTokenData.getName());
                request.setAttribute("userType", validTokenData.getUserType());
                filterChain.doFilter(request, response);
            }
        } catch (Exception e){
            log.error("doFilterInternal() - 'mensagem de erro': {}", e.getMessage());
            throw new JwtFilterExecption(e.getMessage());
        }
    }
}