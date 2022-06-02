package com.sistema.blog.sistemablogspringboot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter @NoArgsConstructor
public class ComentarioDTO {

    private Long id;

    @NotEmpty(message = "El nombre no debe estar vacio")
    private String nombre;

    @NotEmpty(message = "El email no debe estar vacio")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, message = "El contenido del comentario debe ser mayor a 3 caracteres")
    private String cuerpo;

}
