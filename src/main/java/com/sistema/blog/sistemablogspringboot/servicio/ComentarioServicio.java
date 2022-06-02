package com.sistema.blog.sistemablogspringboot.servicio;

import com.sistema.blog.sistemablogspringboot.dto.ComentarioDTO;
import com.sistema.blog.sistemablogspringboot.entidades.Comentario;

import java.util.List;

public interface ComentarioServicio {

    ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO);

    List<ComentarioDTO> obtenerComentariosPorPublicacion(Long publicacionId);

    ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId);

    ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId , ComentarioDTO solicitudDeComentario);

    void eliminarComentario(Long publicacionId, Long comentarioId);
}
