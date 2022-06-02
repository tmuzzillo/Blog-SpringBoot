package com.sistema.blog.sistemablogspringboot.repositorio;

import com.sistema.blog.sistemablogspringboot.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositorio extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);

}
