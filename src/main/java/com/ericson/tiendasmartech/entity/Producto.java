package com.ericson.tiendasmartech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sku;
    private String nombre;
    private String descripcion;
    private String slogan;

    @ManyToOne
    @JoinColumn(name = "marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    private double precio;
    private int descuento;
    private int stock;
    private boolean estado;
    private Date registro;
    private Date actualiza;

    @OneToMany(mappedBy = "producto")
    private List<Foto> fotos;

    @OneToMany(mappedBy = "producto")
    private List<Especificacion> especificaciones;

}
