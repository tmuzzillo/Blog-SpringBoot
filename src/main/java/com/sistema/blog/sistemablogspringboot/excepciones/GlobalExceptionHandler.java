package com.sistema.blog.sistemablogspringboot.excepciones;

import com.sistema.blog.sistemablogspringboot.dto.ErrorDetalles;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //Maneja la captura de todas las excepciones de la aplicacion
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class) //Maneja la excepcion ResourceNotFoundException
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppException.class) //Maneja la excepcion BlogAppException
    public ResponseEntity<ErrorDetalles> manejarBlogAppException(BlogAppException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class) //Maneja la excepcion GlobalException
    public ResponseEntity<ErrorDetalles> manejarGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Implementamos metodo heredado de ResponseEntityExceptionHandler para manejar una excepcion cuando los datos ingresados no son validos porque rompen la validacion especificada (ej: notEmpty, size min or max, etc)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        //Obtenemos todos los errores y los recorremos con forEach
        ex.getBindingResult().getAllErrors().forEach((error)->{
            //Obtenemos el campo en donde esta el error
            String nombreCampo = ((FieldError)error).getField();
            //Obtenemos el mensaje del error
            String mensaje = error.getDefaultMessage();
            //Colocamos los valores dentro del mapa(key:nombre del campo del error, value: mensaje de error)
            errores.put(nombreCampo,mensaje);

        }) ;
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
