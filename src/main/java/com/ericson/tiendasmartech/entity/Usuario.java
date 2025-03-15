package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.Documento;
import com.ericson.tiendasmartech.enums.Rol;
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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Documento documento;

    private String numero;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String email;
    private String password;
    private boolean estado;
    private boolean verificado;
    private Date nacimiento;

    @Column(updatable = false)
    private Date registro;
    private Date actualiza;

    @OneToMany(mappedBy = "usuario")
    private List<Direccion> direcciones;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;

    @PrePersist
    private void prePersist() {
        estado = true;
        verificado = false;
        registro = new Date();
        actualiza = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        actualiza = new Date();
    }
}
