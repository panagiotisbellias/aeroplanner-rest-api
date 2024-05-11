package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Log LOG = LogFactory.getLog(JwtServiceImpl.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Override
    public String extractUsername(String token) {
        LOG.debug(String.format("extractUsername(%s)", token));
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        LOG.debug(String.format("extractClaim(%s, %s)", token, claimsResolver));
        final Claims claims = extractAllClaims(token);
        LOG.debug(String.format("Claims : %s", claims));
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        LOG.debug(String.format("generateToken(%s)", userDetails));
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        LOG.debug(String.format("generateToken(%s, %s)", extraClaims, userDetails));
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    @Override
    public long getExpirationTime() {
        LOG.debug("getExpirationTime()");
        return jwtExpiration;
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        LOG.debug(String.format("buildToken(%s, %s, %d)", extraClaims, userDetails, jwtExpiration));
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        LOG.debug(String.format("isTokenValid(%s, %s)", token, userDetails));
        final String username = extractUsername(token);
        LOG.debug(String.format("Username is %s", username));
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        LOG.debug(String.format("isTokenExpired(%s)", token));
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        LOG.debug(String.format("extractExpiration(%s)", token));
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        LOG.debug(String.format("extractAllClaims(%s)", token));
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        LOG.debug("getSigningKey()");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        LOG.debug(String.format("Key bytes are : %s", Arrays.toString(keyBytes)));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
