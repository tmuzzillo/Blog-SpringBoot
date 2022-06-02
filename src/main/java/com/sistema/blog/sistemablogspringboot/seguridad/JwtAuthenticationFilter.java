package com.sistema.blog.sistemablogspringboot.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtenemos el token del header con el metodo que definimos abajo
        String token = obtenerJWTdeLaSolicitud(request);

        //validamos el token
        if (StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
            //obtenemos el usuario del token
            String username = jwtTokenProvider.obtenerUsernameDelJWT(token);

            //cargamos el usuario asociado al token en el contexto de spring security
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //establecemos la seguridad en el contexto de spring security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        //continuamos con el filtro para la peticion
        filterChain.doFilter(request, response);
    }

    //Bearer token de acceso
    private String obtenerJWTdeLaSolicitud(HttpServletRequest request){
        //obtenemos el header de la solicitud
        String bearerToken = request.getHeader("Authorization");

        //si el header no es null
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            //obtenemos el token
            return bearerToken.substring(7, bearerToken.length()); //el 7 es para eliminar el texto Bearer del token y que nos quede el token solo
        }

        return null;
    }
}
