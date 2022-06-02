package com.sistema.blog.sistemablogspringboot.servicio;

import com.sistema.blog.sistemablogspringboot.dto.PublicacionDTO;
import com.sistema.blog.sistemablogspringboot.dto.PublicacionRespuesta;
import com.sistema.blog.sistemablogspringboot.entidades.Publicacion;
import com.sistema.blog.sistemablogspringboot.excepciones.ResourceNotFoundException;
import com.sistema.blog.sistemablogspringboot.repositorio.PublicacionRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServicioImp implements PublicacionServicio{

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        //Convertimos de DTO a entidad (lo pasamos a objeto para luego guardarlo en la base de datos con repositorio.save
        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);

        //Convertimos de entidad a DTO (JSON)
        PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
        return publicacionRespuesta;

    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String orden) {
        Sort sort = orden.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending() : Sort.by(ordenarPor).descending();
        //Aplicamos paginacion a la lista de publicaciones
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

        //Tomamos la lista de publicaciones del repositorio
        List<Publicacion> listaDePublicaciones = publicaciones.getContent();

        //Mapeamos cada objeto de publicacion de esa lista a DTO (JSON) y lo mostramos en otra lsita con toList
        List<PublicacionDTO> contenido = listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());

        //Creamos un objeto de respuesta con los datos de la paginacion
        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setEsUltimaPagina(publicaciones.isLast());
        return publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);

        return mapearDTO(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacion(long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        publicacionRepositorio.delete(publicacion);

    }

    //Convierte entidad a DTO (JSON)
    private PublicacionDTO mapearDTO(Publicacion publicacion){
        //Usamos el modelMapper para mapear de entidad a DTO (primer parametro es la entidad, segundo parametro es el DTO)
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);

        return publicacionDTO;
    }

    //Convierte de DTO a entidad
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){
        //Usamos el modelMapper para mapear de DTO a entidad (primer parametro es el DTO, segundo parametro es la entidad)
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
        return publicacion;
    }


}
