package com.study.security.security.jwt;

import com.study.security.dto.user.UserRequestDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Service
public class TokenJwtService {
    private final String EXPIRATION = "30";
    private final String KEY_SIGNATURE = "keySignature";

    public String buildToken(UserRequestDTO userRequestDTO) {
        final long expirationValue = Long.valueOf(EXPIRATION);
        final LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(expirationValue);
        final Instant instant = expirationDate.atZone(ZoneId.systemDefault()).toInstant();
        return Jwts
                .builder()
                .setSubject(userRequestDTO.getLogin())
                .setExpiration(Date.from(instant))
                .signWith(SignatureAlgorithm.HS512, KEY_SIGNATURE)
                .compact();
    }

    public String getLoggedUser(String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }

    public boolean tokenValid(String token) {
        try{
            final Claims claims = getClaims(token);
            final Date expirationDate = claims.getExpiration();
            final LocalDateTime data = expirationDate
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                 .parser()
                 .setSigningKey(KEY_SIGNATURE)
                 .parseClaimsJws(token)
                 .getBody();
    }
}
