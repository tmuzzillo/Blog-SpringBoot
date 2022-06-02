package com.sistema.blog.sistemablogspringboot.controlador;


import com.sistema.blog.sistemablogspringboot.dto.ComentarioDTO;
import com.sistema.blog.sistemablogspringboot.servicio.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacion(@PathVariable(name = "publicacionId") Long publicacionId) {
        return comentarioServicio.obtenerComentariosPorPublicacion(publicacionId);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(name = "publicacionId") Long publicacionId, @PathVariable(name = "id") Long comentarioId) {
        ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@Valid @RequestBody ComentarioDTO comentarioDTO, @PathVariable(name = "publicacionId") Long publicacionId) {
        return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@Valid @RequestBody ComentarioDTO comentarioDTO, @PathVariable(name = "publicacionId") Long publicacionId, @PathVariable(name = "id") Long comentarioId) {
        ComentarioDTO comentarioActualizado = comentarioServicio.actualizarComentario(publicacionId, comentarioId, comentarioDTO);
        return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(name = "publicacionId") Long publicacionId, @PathVariable(name = "id") Long comentarioId) {
        comentarioServicio.eliminarComentario(publicacionId, comentarioId);
        return new ResponseEntity<>("Comentario eliminado con exito",HttpStatus.OK);
    }




}
