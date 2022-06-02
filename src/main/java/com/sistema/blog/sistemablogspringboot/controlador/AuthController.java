package com.sistema.blog.sistemablogspringboot.controlador;


import com.sistema.blog.sistemablogspringboot.dto.LoginDTO;
import com.sistema.blog.sistemablogspringboot.dto.RegistroDTO;
import com.sistema.blog.sistemablogspringboot.entidades.Rol;
import com.sistema.blog.sistemablogspringboot.entidades.Usuario;
import com.sistema.blog.sistemablogspringboot.repositorio.RolRepositorio;
import com.sistema.blog.sistemablogspringboot.repositorio.UsuarioRepositorio;
import com.sistema.blog.sistemablogspringboot.seguridad.JwtAuthResponseDTO;
import com.sistema.blog.sistemablogspringboot.seguridad.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        //Guardamos en un objeto authentication el usuario y contraseña que nos llega por el JSON en el objeto loginDTO
        //LoginDTO tiene el usuario o email y la password con la que se loguea
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.generarToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO) {

        if (usuarioRepositorio.existsByUsername(registroDTO.getUsername())) {

            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }
        if (usuarioRepositorio.existsByEmail(registroDTO.getEmail())) {
                return new ResponseEntity<>("Username already in use", HttpStatus.BAD_REQUEST);
        }

        //Creamos un nuevo usuario
        //Usuario tiene nombre, username, email, password, rol
        //Rol tiene nombre
        //El password lo codificamos con el passwordEncoder
        //El rol lo buscamos por su nombre
        //El usuario lo guardamos en la base de datos
        //El usuario lo guardamos en el contexto de seguridad
        //Devolvemos un mensaje de OK
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepositorio.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepositorio.save(usuario);

        return new ResponseEntity<>("Registrado exitosamente", HttpStatus.OK);
    }
}
