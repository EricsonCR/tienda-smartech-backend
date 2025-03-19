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
        List<PedidoDetalleDto> lista = toPedidoDetalleDto(pedido.getPedidoDetalles());
        return new PedidoDto(pedido.getId(), pedido.getNumero(), pedido.getEstado(), usuario, pedido.getEntrega(), direccion, pedido.getPrecio_envio(), pedido.getPrecio_cupon(), pedido.getTotal(), pedido.getIgv(), pedido.getComentarios(), pedido.getFecha_entrega(), lista);
    }

    public Pedido toEntity(PedidoDto pedidoDto) {
        Usuario usuario = toUsuarioEntity(pedidoDto.usuario());
        Direccion direccion = toDireccionEntity(pedidoDto.direccion());
        List<PedidoDetalle> lista = toPedidoDetalleEntity(pedidoDto.pedidoDetalles());
        return new Pedido(0, null, pedidoDto.estado(), usuario, pedidoDto.entrega(), direccion, pedidoDto.precio_envio(), pedidoDto.precio_cupon(), pedidoDto.total(), 0, pedidoDto.comentarios(), pedidoDto.fecha_entrega(), null, null, lista);
    }

    private Usuario toUsuarioEntity(UsuarioDto u) {
        return new Usuario(u.id(), u.documento(), u.numero(), u.rol(), u.nombres(), u.apellidos(), u.direccion(), u.telefono(), u.email(), null, false, false, u.nacimiento(), null, null, null, null);
    }

    private Direccion toDireccionEntity(DireccionDto d) {
        return new Direccion(d.id(), null, d.via(), d.documento(), d.numero(), d.nombres(), d.celular(), d.direccion(), d.referencia(), d.distrito(), d.provincia(), d.departamento(), d.codigo_postal(), null, null);
    }

    private List<PedidoDetalle> toPedidoDetalleEntity(List<PedidoDetalleDto> detalles) {
        List<PedidoDetalle> lista = new ArrayList<>();
        for (PedidoDetalleDto item : detalles) {
            ProductoDto p = item.producto();
            Producto producto = new Producto(p.id(), p.sku(), p.nombre(), p.descripcion(), p.slogan(), null, null, p.precio(), p.descuento(), p.stock(), false, null, null, null, null);
            lista.add(new PedidoDetalle(0, null, producto, item.cantidad(), item.precio()));
        }
        return lista;
    }

    private UsuarioDto toUsuarioDto(Usuario usuario) {
        return new UsuarioDto(usuario.getId(), usuario.getDocumento(), usuario.getNumero(), usuario.getRol(), usuario.getNombres(), usuario.getApellidos(), usuario.getDireccion(), usuario.getTelefono(), usuario.getEmail(), usuario.getNacimiento(), null, null);
    }

    private DireccionDto toDireccionDto(Direccion direccion) {
        return new DireccionDto(direccion.getId(), null, direccion.getVia(), direccion.getDocumento(), direccion.getNumero(), direccion.getNombres(), direccion.getCelular(), direccion.getDireccion(), direccion.getReferencia(), direccion.getDistrito(), direccion.getProvincia(), direccion.getDepartamento(), direccion.getCodigo_postal());
    }

    private List<PedidoDetalleDto> toPedidoDetalleDto(List<PedidoDetalle> detalles) {
        List<PedidoDetalleDto> lista = new ArrayList<>();
        for (PedidoDetalle item : detalles) {
            lista.add(new PedidoDetalleDto(item.getId(), null, toProductoDto(item.getProducto()), item.getCantidad(), item.getPrecio()));
        }
        return lista;
    }

    private ProductoDto toProductoDto(Producto producto) {
        return new ProductoDto(producto.getId(), producto.getSku(), producto.getNombre(), producto.getDescripcion(), producto.getSlogan(), null, null, producto.getPrecio(), producto.getDescuento(), producto.getStock(), null, null);
    }
}
