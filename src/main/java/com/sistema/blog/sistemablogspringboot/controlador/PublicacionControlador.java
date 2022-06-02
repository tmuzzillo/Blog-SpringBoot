package com.sistema.blog.sistemablogspringboot.controlador;


import com.sistema.blog.sistemablogspringboot.dto.PublicacionDTO;
import com.sistema.blog.sistemablogspringboot.dto.PublicacionRespuesta;
import com.sistema.blog.sistemablogspringboot.servicio.PublicacionServicio;
import com.sistema.blog.sistemablogspringboot.utilerias.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {

    @Autowired
    private PublicacionServicio publicacionServicio;

    // aplicamos el parametro RequestParam para aplicar la paginacion implementada en el servicio
    @GetMapping
    public PublicacionRespuesta listarPublicaciones(@RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENARPOR_POR_DEFECTO, required = false) String ordenarPor,
                                                    @RequestParam(value = "sortOrder", defaultValue = AppConstantes.ORDEN_POR_DEFECTO, required = false) String orden) {
        return publicacionServicio.obtenerTodasLasPublicaciones(numeroDePagina, medidaDePagina, ordenarPor, orden);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
    }

    //indicamos que para realizar la operacion necesita estar registrado con el rol de ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){ //La anotacion Valid implementa la validacion hecha en la clase PublicacionDTO

        return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO, @PathVariable(name = "id") long id){
        PublicacionDTO publicacionActualizada = publicacionServicio.actualizarPublicacion(publicacionDTO, id);
        return new ResponseEntity<>(publicacionActualizada, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id){
        publicacionServicio.eliminarPublicacion(id);
        return new ResponseEntity<>("Publicacion eliminada con exito ", HttpStatus.OK);
    }

}
