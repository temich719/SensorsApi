package com.lab.sensors.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.sensors.answer.AnswerMessage;
import com.lab.sensors.detailsService.CustomUserDetailsService;
import com.lab.sensors.security.jwtProvider.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private JwtProvider jwtProvider;
    private CustomUserDetailsService userDetailsService;
    private ObjectMapper objectMapper;
    public static final int CODE_AUTHENTICATION_EXCEPTION = 55;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        try {
            Optional<String> token = jwtProvider.resolveToken((HttpServletRequest) request);
            if (token.isPresent() && jwtProvider.validateToken(token.get())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtProvider.getLoginFromToken(token.get()));
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            AnswerMessage answerMessage = new AnswerMessage();
            answerMessage.setCode(String.valueOf(CODE_AUTHENTICATION_EXCEPTION));
            answerMessage.setStatus(HttpStatus.FORBIDDEN.toString());
            answerMessage.setMessage("Access is forbidden");
            ((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(objectMapper.writeValueAsString(answerMessage));
        }
    }

}
