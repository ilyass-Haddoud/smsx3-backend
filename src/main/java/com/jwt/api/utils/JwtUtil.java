package com.jwt.api.utils;

import com.jwt.api.supplier.Supplier;
import com.jwt.api.user.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final String secret_key ;

    private final long accessTokenValidity;

    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil(@Value("${jwt_secret_key}") String secret_key, @Value("${jwt_validity}") long accessTokenValidity){
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
        this.secret_key = secret_key;
        this.accessTokenValidity = accessTokenValidity;
    }

    public String createToken(Object entity,List<String> roles) {
        Claims claims = Jwts.claims();
        String subject = null;
        String email = null;
        String bpsnum = null;

        if (entity instanceof User user) {
            subject = user.getEmail();
            email = user.getEmail();
            claims.put("name", user.getName());
        } else if (entity instanceof Supplier supplier) {
            subject = supplier.getBpsnum();
            email = supplier.getBpsaddeml();
            bpsnum = supplier.getBpsnum();
            claims.put("name", bpsnum);
        }

        claims.setSubject(subject);

        claims.put("email", email);
        claims.put("roles", roles);

        Date tokenValidity = new Date(System.currentTimeMillis() + accessTokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw new SessionAuthenticationException("Token expired, please reconnect");
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}