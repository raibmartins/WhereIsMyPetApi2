package com.unesc.net.WhereIsMyPet.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.unesc.net.WhereIsMyPet.entity.user.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;


    public String gerar(Usuario user) {
        return JWT.create()
                .withIssuer("whereIsMyPet")
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withExpiresAt(
                        LocalDateTime.now().plusDays(1)
                                .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(secret));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).withIssuer("whereIsMyPet").build().verify(token).getSubject();
    }

}
