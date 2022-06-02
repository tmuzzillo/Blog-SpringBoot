package com.sistema.blog.sistemablogspringboot.excepciones;


import com.sistema.blog.sistemablogspringboot.entidades.Publicacion;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//En caso de que no encuentre una publicacion con el id indicado
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private String nombreDelRecurso;
    @Getter @Setter
    private String nombreDelCampo;
    @Getter @Setter
    private long valorDelCampo;

    public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, long valorDelCampo) {
        super(String.format("%s no encontrada con: %s : '%s' ", nombreDelRecurso, nombreDelCampo, valorDelCampo));
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelCampo = valorDelCampo;
    }


}
