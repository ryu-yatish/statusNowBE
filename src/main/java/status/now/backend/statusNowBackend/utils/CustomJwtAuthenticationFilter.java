package status.now.backend.statusNowBackend.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
@Component
@Slf4j
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final AuthenticationFailureHandler failureHandler;

    public CustomJwtAuthenticationFilter(JwtDecoder jwtDecoder, AuthenticationFailureHandler failureHandler) {
        this.jwtDecoder = jwtDecoder;
        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = extractToken(request);
            if (token != null) {

                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")); // Adjust as needed

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(token, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            // Proceed with the filter chain
            filterChain.doFilter(request, response);
        } catch (BadJwtException ex) {
            log.error("JWT validation failed: " + ex.toString());
            SecurityContextHolder.clearContext();
            failureHandler.onAuthenticationFailure(request, response, new AuthenticationException("Token validation failed") {});
        }
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
