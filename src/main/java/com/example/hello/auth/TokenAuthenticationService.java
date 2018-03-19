package com.example.hello.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Component
public class TokenAuthenticationService {

    static final long EXPIRATION_TIME = 64_000_000;
    static final String SECRET = "ThisIsSecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static String createJWTAuthentication(HttpServletResponse response, String username, String id, UserContext userContext) {

        String JWT = Jwts.builder().setSubject(username).setId(id).claim("userDetails", userContext)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();

        return JWT;
    }

    static Authentication getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            try {
                Claims claim = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

                HashMap<String, Object> userDetails = (HashMap<String, Object>) claim.get("userDetails");

                Long id = Long.parseLong(userDetails.get("id").toString());
                String username = userDetails.get("username").toString();

                UserContext userContext = new UserContext(id, username);

                return new UsernamePasswordAuthenticationToken(userContext, null, new ArrayList<>());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
