package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.Documento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consignatarios")
public class Consignatario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Documento documento;
    private String numero;
    private String nombres;
    private String celular;
    private String email;
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
