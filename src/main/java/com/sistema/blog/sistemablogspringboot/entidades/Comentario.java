package com.sistema.blog.sistemablogspringboot.entidades;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String nombre;
    private String email;
    private String cuerpo;

    //RELACIONES
    //Muchos comentarios pertenecen a una publicacion
    @ManyToOne(fetch = FetchType.LAZY) //LAZY: Se carga solo cuando se necesita
    @JoinColumn(name = "publicacion_id", nullable = false)
    private Publicacion publicacion;



}
