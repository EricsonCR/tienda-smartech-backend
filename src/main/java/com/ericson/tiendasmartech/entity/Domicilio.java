package com.ericson.tiendasmartech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "domicilios")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "consignatario")
    private Consignatario consignatario;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "direccion")
    private Direccion direccion;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @Column(updatable = false)
    private Date registro;
    private Date actualiza;

    @PrePersist
    public void prePersist() {
        registro = new Date();
        actualiza = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        actualiza = new Date();
    }
}
