package status.now.backend.statusNowBackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${secret.key}")
    private String secretKey; // Change this to a secure key
    final long expirationTime = 1000 * 60 * 60; // 1 hour

    public String generateToken(String username) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        return JWT.create().withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(new Date(nowMillis + expirationTime))
                .sign(Algorithm.HMAC256( secretKey));
    }
}
