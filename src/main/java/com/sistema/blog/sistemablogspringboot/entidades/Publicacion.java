package com.sistema.blog.sistemablogspringboot.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "publicaciones", uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo"})})
public class Publicacion {

    @Id
    @Setter @Getter @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Getter @Column(name = "titulo", nullable = false)
    private String titulo;

    @Setter @Getter @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Setter @Getter @Column(name = "contenido", nullable = false)
    private String contenido;

    //RELACIONES
    //Establecemos relacion de una publicacion tiene muchos comentarios
    @Setter @Getter
    @JsonBackReference  //Resuelve el problema de referencia circular o serialize (error infinito) en el json
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true) //Si eliminamos una publicacion, se eliminan sus comentarios (cacade y orphanRemoval)
    private Set<Comentario> comentarios = new HashSet<>();


}
