package com.vineyard.microservicios_cliente.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String appaterno;

    
    @Column(nullable = false)
    private String correo;

    
    @Column(nullable = false)
    private String telefono;

    
    @Column(nullable = false)
    private String direccion;
}
