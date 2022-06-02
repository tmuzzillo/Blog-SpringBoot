package com.sistema.blog.sistemablogspringboot.repositorio;


import com.sistema.blog.sistemablogspringboot.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

    List<Comentario> findByPublicacionId(Long publicacionId);

}




