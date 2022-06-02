package com.sistema.blog.sistemablogspringboot.seguridad;


import com.sistema.blog.sistemablogspringboot.excepciones.BlogAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private String jwtExpirationInMs;


    public String generarToken(Authentication authentication) {
        //obtenemos el usuario del token
        String username = authentication.getName();
        //calculamos la fecha de expiracion del token
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + Long.parseLong(jwtExpirationInMs));

        //generamos el token
       return Jwts.builder()
               .setSubject(username)
               .setIssuedAt(fechaActual)
               .setExpiration(fechaExpiracion)
               .signWith(SignatureAlgorithm.HS512, jwtSecret)
               .compact();
    }

    public String obtenerUsernameDelJWT(String token){
        //obtenemos los claims del token (datos del usuario)
        Claims claims = Jwts.parser()
                                .setSigningKey(jwtSecret)
                                .parseClaimsJws(token)
                                .getBody();
        return claims.getSubject();
    }

    //metodo que valida el token
    public boolean validarToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;

        }catch (SignatureException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Firma JWT inválida");
        }
        catch (MalformedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El token JWT está malformado");
        }
        catch (ExpiredJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El token JWT ha expirado");
        }
        catch (UnsupportedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El token JWT no es compatible con el algoritmo");
        }
        catch (IllegalArgumentException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"La cadena claims JWT está vacía");
        }

    }



}
