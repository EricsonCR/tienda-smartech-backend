package com.ericson.tiendasmartech.entity;

import com.ericson.tiendasmartech.enums.Entrega;
import com.ericson.tiendasmartech.enums.EstadoPedido;
import com.ericson.tiendasmartech.enums.MetodoPago;
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
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String numero;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @ManyToOne()
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Entrega entrega;

    @ManyToOne
    @JoinColumn(name = "consignatario")
    private Consignatario consignatario;

    @ManyToOne()
    @JoinColumn(name = "direccion")
    private Direccion direccion;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodo_pago;
    private double precio_envio;
    private double precio_cupon;
    private double total;
    private double igv;
    private String comentarios;

    private Date fecha_entrega;

    @Column(updatable = false)
    private Date registro;
    private Date actualiza;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoDetalle> pedidoDetalles;

    @PrePersist
    private void prePersist() {
        registro = new Date();
        actualiza = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        actualiza = new Date();
    }

}
