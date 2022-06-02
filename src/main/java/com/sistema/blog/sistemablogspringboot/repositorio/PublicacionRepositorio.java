package com.sistema.blog.sistemablogspringboot.repositorio;

import com.sistema.blog.sistemablogspringboot.entidades.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {


}
