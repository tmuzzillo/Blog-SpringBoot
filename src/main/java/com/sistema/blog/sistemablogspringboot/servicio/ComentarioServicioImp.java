package com.sistema.blog.sistemablogspringboot.servicio;

import com.sistema.blog.sistemablogspringboot.dto.ComentarioDTO;
import com.sistema.blog.sistemablogspringboot.entidades.Comentario;
import com.sistema.blog.sistemablogspringboot.entidades.Publicacion;
import com.sistema.blog.sistemablogspringboot.excepciones.BlogAppException;
import com.sistema.blog.sistemablogspringboot.excepciones.ResourceNotFoundException;
import com.sistema.blog.sistemablogspringboot.repositorio.ComentarioRepositorio;
import com.sistema.blog.sistemablogspringboot.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServicioImp implements ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {
        //Mapeamos el DTO a una entidad
        Comentario comentario = mapearEntidad(comentarioDTO);
        //Buscar publicacion
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        //Asignamos un comentario a la publicacion
        comentario.setPublicacion(publicacion);

        //Guardamos el comentario
        Comentario nuevoComentario = comentarioRepositorio.save(comentario);

        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacion(Long publicacionId) {
        //Buscamos comentarios por el id de una publicacion
        List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);

        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        //Buscamos publicacion
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        //Buscamos comentario
        Comentario comentario = comentarioRepositorio.findById(comentarioId).orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        //Verificamos que el comentario pertenezca a la publicacion
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion"); //No anda BlogAppException
        }

        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId , ComentarioDTO solicitudDeComentario) {
        //Buscamos publicacion
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        //Buscamos comentario
        Comentario comentario = comentarioRepositorio.findById(comentarioId).orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        //Verificamos que el comentario pertenezca a la publicacion
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion"); //No anda BlogAppException
        }
        //Seteamos los valores nuevos del comentario
        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        //Guardamos el comentario y lo mapeamos a un DTO
        Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        //Buscamos publicacion
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        //Buscamos comentario
        Comentario comentario = comentarioRepositorio.findById(comentarioId).orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        //Verificamos que el comentario pertenezca a la publicacion
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion"); //No anda BlogAppException
        }

        //Eliminamos el comentario
        comentarioRepositorio.delete(comentario);
    }

    //mapear comentario a DTO
    private ComentarioDTO mapearDTO(Comentario comentario) {
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
        return comentarioDTO;

    }

    //mapearDTO a comentario
    private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        return comentario;
    }

}

