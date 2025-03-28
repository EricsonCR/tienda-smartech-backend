package com.ericson.tiendasmartech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favoritos")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "producto")
    private Producto producto;

    @Column(updatable = false)
    LocalDateTime registro;

    @PrePersist
    public void prePersist(){
        registro = LocalDateTime.now();
    }
}
