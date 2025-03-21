package com.ericson.tiendasmartech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oficinas")
public class Oficina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String celular;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "direccion")
    private Direccion direccion;
    private LocalTime hora_inicio;
    private LocalTime hora_fin;
    @Column(updatable = false)
    private LocalDateTime registro;
    private LocalDateTime actualiza;

    @PrePersist
    public void prePersist() {
        registro = LocalDateTime.now();
        actualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        actualiza = LocalDateTime.now();
    }
}
