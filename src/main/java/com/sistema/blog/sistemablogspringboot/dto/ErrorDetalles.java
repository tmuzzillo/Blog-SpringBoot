package com.sistema.blog.sistemablogspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor @Getter @Setter
public class ErrorDetalles {

    private Date marcaDeTiempo;
    private String mensaje;
    private String detalles;

}
