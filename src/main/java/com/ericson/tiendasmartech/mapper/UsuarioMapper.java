package com.ericson.tiendasmartech.mapper;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioMapper {
    public UsuarioDto toDto(Usuario u) {
        List<DomicilioDto> domicilios = toListDomicilioDto(u.getDomicilios());
        List<PedidoDto> pedidos = toListPedidoDto(u.getPedidos());
        return new UsuarioDto(
                u.getId(),
                u.getDocumento(),
                u.getNumero(),
                u.getRol(),
                u.getNombres(),
                u.getApellidos(),
                u.getDireccion(),
                u.getTelefono(),
                u.getEmail(),
                u.getNacimiento(),
                domicilios,
                pedidos
        );
    }

    public Usuario toEntity(UsuarioDto usuarioDto) {
        return null;
    }

    private List<DomicilioDto> toListDomicilioDto(List<Domicilio> domicilios) {
        List<DomicilioDto> lista = new ArrayList<>();
        for (Domicilio d : domicilios) {
            DireccionDto direccion = toDireccionDto(d.getDireccion());
            ConsignatarioDto consignatario = toConsignatarioDto(d.getConsignatario());
            lista.add(new DomicilioDto(d.getId(), consignatario, direccion, null));
        }
        return lista;
    }

    private List<PedidoDto> toListPedidoDto(List<Pedido> pedidos) {
        List<PedidoDto> lista = new ArrayList<>();
        for (Pedido p : pedidos) lista.add(toPedidoDto(p));
        return lista;
    }

    private List<PedidoDetalleDto> toListPedidoDetalleDto(List<PedidoDetalle> detalles) {
        List<PedidoDetalleDto> lista = new ArrayList<>();
        for (PedidoDetalle detalle : detalles) {
            lista.add(toPedidoDetalleDto(detalle));
        }
        return lista;
    }

    private PedidoDetalleDto toPedidoDetalleDto(PedidoDetalle detalle) {
        return new PedidoDetalleDto(
                detalle.getId(),
                null,
                toProductoDto(detalle.getProducto()),
                detalle.getCantidad(),
                detalle.getPrecio()
        );
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
                null
        );
    }

    private PedidoDto toPedidoDto(Pedido pedido) {
        return new PedidoDto(
                pedido.getId(),
                pedido.getNumero(),
                pedido.getEstado(),
                null,
                pedido.getEntrega(),
                null,
                //toConsignatarioDto(pedido.getConsignatario()),
                toDireccionDto(pedido.getDireccion()),
                pedido.getMetodo_pago(),
                pedido.getPrecio_envio(),
                pedido.getPrecio_cupon(),
                pedido.getTotal(),
                pedido.getIgv(),
                pedido.getComentarios(),
                pedido.getFecha_entrega(),
                toListPedidoDetalleDto(pedido.getPedidoDetalles())
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

    private DireccionDto toDireccionDto(Direccion direccion) {
        return new DireccionDto(
                direccion.getId(),
                direccion.getVia(),
                direccion.getNombre(),
                direccion.getNumero(),
                direccion.getReferencia(),
                direccion.getDistrito(),
                direccion.getProvincia(),
                direccion.getDepartamento(),
                direccion.getCodigo_postal()
        );
    }
}
