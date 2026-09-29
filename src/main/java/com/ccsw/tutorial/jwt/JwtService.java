package com.ccsw.tutorial.jwt;

import com.ccsw.tutorial.role.model.Role;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    // De momento la clave la creamos al arrancar, pero se puede guardar de forma persistente.
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String getToken(String name, Role role) {

        return Jwts.builder().subject(name).claim("role", role.getName()).signWith(key).compact();
    }
}
