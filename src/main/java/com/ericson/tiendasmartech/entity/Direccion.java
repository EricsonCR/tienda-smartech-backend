package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.Via;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Via via;

    private String nombre;
    private int numero;
    private String referencia;
    private String distrito;
    private String provincia;
    private String departamento;
    private int codigo_postal;

    @Column(updatable = false)
    private Date registro;
    private Date actualiza;

    @PrePersist
    private void PrePersist() {
        registro = new Date();
        actualiza = new Date();
        distrito = distrito.toUpperCase();
        provincia = provincia.toUpperCase();
        departamento = departamento.toUpperCase();
    }

    @PreUpdate
    private void PreUpdate() {
        actualiza = new Date();
    }
}
