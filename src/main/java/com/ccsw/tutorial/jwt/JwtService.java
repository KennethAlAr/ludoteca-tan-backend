package com.ccsw.tutorial.jwt;

import com.ccsw.tutorial.role.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    // De momento la clave la creamos al arrancar, pero se puede guardar de forma persistente.
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String getToken(String name, Role role) {

        return Jwts.builder().subject(name).claim("role", role.getName()).signWith(key).compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public void validateToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no proporcionado.");
        }

        String[] parts = authorizationHeader.split(" ");

        String prefix = parts[0];
        String token = parts[1];

        if (!"Bearer".equals(prefix)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Formato de token no válido.");
        }

        String role = getClaims(token).get("role", String.class);

        if (!"ADMIN".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes autorización para llevar a cabo esa acción.");
        }
    }
}
