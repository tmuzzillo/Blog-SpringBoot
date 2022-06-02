package com.sistema.blog.sistemablogspringboot.seguridad;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class JwtAuthResponseDTO {

    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";


    public JwtAuthResponseDTO(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

}
