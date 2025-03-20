package com.ericson.tiendasmartech.mapper;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoMapper {
    public PedidoDto toDto(Pedido pedido) {
        UsuarioDto usuario = toUsuarioDto(pedido.getUsuario());
        DireccionDto direccion = toDireccionDto(pedido.getDireccion());
        ConsignatarioDto consignatario = toConsignatarioDto(pedido.getConsignatario());
        List<PedidoDetalleDto> pedidos = toListPedidoDetalleDto(pedido.getPedidoDetalles());
        return new PedidoDto(
                pedido.getId(),
                pedido.getNumero(),
                pedido.getEstado(),
                usuario,
                pedido.getEntrega(),
                consignatario,
                direccion,
                pedido.getMetodo_pago(),
                pedido.getPrecio_envio(),
                pedido.getPrecio_cupon(),
                pedido.getTotal(),
                pedido.getIgv(),
                pedido.getComentarios(),
                pedido.getFecha_entrega(),
                pedidos
        );
    }

    public Pedido toEntity(PedidoDto pedido) {
        Usuario usuario = toUsuarioEntity(pedido.usuario());
        Direccion direccion = toDireccionEntity(pedido.direccion());
        Consignatario consignatario = toConsignatarioEntity(pedido.consignatario());
        List<PedidoDetalle> pedidos = toListPedidoDetalleEntity(pedido.pedidoDetalles());
        return new Pedido(
                0,
                null,
                pedido.estado(),
                usuario,
                pedido.entrega(),
                consignatario,
                direccion,
                pedido.metodo_pago(),
                pedido.precio_envio(),
                pedido.precio_cupon(),
                pedido.total(),
                0,
                pedido.comentarios(),
                pedido.fecha_entrega(),
                null,
                null,
                pedidos
        );
    }

    private Usuario toUsuarioEntity(UsuarioDto u) {
        return new Usuario(
                u.id(),
                u.documento(),
                u.numero(),
                u.rol(),
                u.nombres(),
                u.apellidos(),
                u.direccion(),
                u.telefono(),
                u.email(),
                null,
                false,
                false,
                u.nacimiento(),
                null,
                null,
                null,
                null);
    }

    private Direccion toDireccionEntity(DireccionDto d) {
        return new Direccion(
                d.id(),
                d.via(),
                d.nombre(),
                d.numero(),
                d.referencia(),
                d.distrito(),
                d.provincia(),
                d.departamento(),
                d.codigo_postal(),
                null,
                null
        );
    }

    private Consignatario toConsignatarioEntity(ConsignatarioDto consignatario) {
        return new Consignatario(
                consignatario.id(),
                consignatario.documento(),
                consignatario.numero(),
                consignatario.nombres(),
                consignatario.celular(),
                consignatario.email(),
                null,
                null
        );
    }

    private List<PedidoDetalle> toListPedidoDetalleEntity(List<PedidoDetalleDto> detalles) {
        List<PedidoDetalle> lista = new ArrayList<>();
        for (PedidoDetalleDto item : detalles) {
            Producto producto = toProductoEntity(item.producto());
            lista.add(new PedidoDetalle(0, null, producto, item.cantidad(), item.precio()));
        }
        return lista;
    }

    private Producto toProductoEntity(ProductoDto p) {
        return new Producto(
                p.id(),
                p.sku(),
                p.nombre(),
                p.descripcion(),
                p.slogan(),
                null,
                null,
                p.precio(),
                p.descuento(),
                p.stock(),
                false,
                null,
                null,
                null,
                null
        );
    }

    private UsuarioDto toUsuarioDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getDocumento(),
                usuario.getNumero(),
                usuario.getRol(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getDireccion(),
                usuario.getTelefono(),
                usuario.getEmail(),
                usuario.getNacimiento(),
                null,
                null);
    }

    private DireccionDto toDireccionDto(Direccion d) {
        return new DireccionDto(
                d.getId(),
                d.getVia(),
                d.getNombre(),
                d.getNumero(),
                d.getReferencia(),
                d.getDistrito(),
                d.getProvincia(),
                d.getDepartamento(),
                d.getCodigo_postal()
        );
    }

    private ConsignatarioDto toConsignatarioDto(Consignatario consignatario) {
        return new ConsignatarioDto(
                consignatario.getId(),
                consignatario.getDocumento(),
                consignatario.getNumero(),
                consignatario.getNombres(),
                consignatario.getCelular(),
                consignatario.getEmail()
        );
    }

    private List<PedidoDetalleDto> toListPedidoDetalleDto(List<PedidoDetalle> detalles) {
        List<PedidoDetalleDto> lista = new ArrayList<>();
        for (PedidoDetalle item : detalles) {
            ProductoDto producto = toProductoDto(item.getProducto());
            lista.add(new PedidoDetalleDto(item.getId(), null, producto, item.getCantidad(), item.getPrecio()));
        }
        return lista;
    }

    private ProductoDto toProductoDto(Producto producto) {
        return new ProductoDto(
                producto.getId(),
                producto.getSku(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getSlogan(),
                null,
                null,
                producto.getPrecio(),
                producto.getDescuento(),
                producto.getStock(),
                null,
                null);
    }
}
