package com.sistema.blog.sistemablogspringboot.dto;


import com.sistema.blog.sistemablogspringboot.entidades.Comentario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class PublicacionDTO {


    private Long id;

    @NotEmpty
    @Size(min = 2, message = "El titulo debe tener al menos 2 caracteres")
    private String titulo;

    @NotEmpty
    @Size(min = 10, message = "La descripcion debe tener al menos 10 caracteres")
    private String descripcion;

    @NotEmpty
    private String contenido;


    private Set<Comentario> comentarios;

}
