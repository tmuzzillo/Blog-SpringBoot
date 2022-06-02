package com.sistema.blog.sistemablogspringboot.repositorio;

import com.sistema.blog.sistemablogspringboot.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    //Busca un usuario por su username, Optional implementa el metodo isPresent(): este devuelve true si encuentra el usuario, false si la posicion es null
    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


}



