package com.sistema.blog.sistemablogspringboot.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "Usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}), @UniqueConstraint(columnNames = {"email"})})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String nombre;

    @Column(length = 60)
    private String username;

    @Column(length = 60)
    private String password;

    @Column(length = 60)
    private String email;

    // RELACIONES
    // Un usuario tiene muchos roles y un rol tienen muchos usuarios (muchos a muchos)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // Creamos tabla intermedia llamada Usuarios_Roles referenciada
    @JoinTable(name = "Usuarios_Roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private Set<Rol> roles = new HashSet<>();

}
