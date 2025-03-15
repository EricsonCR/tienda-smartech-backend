package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.DireccionDto;
import com.ericson.tiendasmartech.dto.PedidoDetalleDto;
import com.ericson.tiendasmartech.dto.PedidoDto;
import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CarritoRepository;
import com.ericson.tiendasmartech.repository.PedidoRepository;
import com.ericson.tiendasmartech.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;

    @Override
    public ServiceResponse registrar(PedidoDto pedidoDto) {
        try {
            Pedido pedido = dtoToEntity(pedidoDto);
            for (PedidoDetalle pedidoDetalle : pedido.getPedidoDetalles())
                pedidoDetalle.setPedido(pedido);
            pedido.setNumero(generarNumeroPedido());
            pedidoRepository.save(pedido);
            Carrito carrito = carritoRepository.findByUsuario(pedido.getUsuario().getId()).orElse(new Carrito());
            carrito.getCarritoDetalles().removeAll(carrito.getCarritoDetalles());
            carritoRepository.save(carrito);
            return new ServiceResponse("Pedido registrado exitosamente", HttpStatus.OK, entityToDto(pedido));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private PedidoDto entityToDto(Pedido pedido) {
        UsuarioDto usuario = new UsuarioDto(pedido.getUsuario().getId(), null, null, null,
                null, null, null, null, null, null, null, null);
        DireccionDto direccion = new DireccionDto(pedido.getDireccion().getId(), null, null, null,
                null, null, null, null, null, null, null,
                null, 0);
        List<PedidoDetalleDto> lista = new ArrayList<>();
        for (PedidoDetalle pedidoDetalle : pedido.getPedidoDetalles()) {
            lista.add(new PedidoDetalleDto(pedidoDetalle.getId(), null, null, 0, 0));
        }
        return new PedidoDto(pedido.getId(), pedido.getNumero(), pedido.getEstado(), usuario, pedido.getEntrega(),
                direccion, pedido.getPrecio_envio(), pedido.getPrecio_cupon(), pedido.getTotal(), pedido.getIgv(),
                pedido.getComentarios(), pedido.getFecha_entrega(), lista);
    }

    private Pedido dtoToEntity(PedidoDto pedidoDto) {
        Usuario usuario = new Usuario(pedidoDto.usuario().id(), null, null, null,
                null, null, null, null, null, null,
                false, false, null, null, null, null, null);
        Direccion direccion = new Direccion(pedidoDto.direccion().id(), usuario, null, null,
                null, null, null, null, null, null, null,
                null, 0, null, null);
        List<PedidoDetalle> lista = new ArrayList<>();
        for (PedidoDetalleDto item : pedidoDto.pedidoDetalles()) {
            Producto producto = new Producto(item.producto().id(), null, null, null,
                    null, null, null, 0, 0, 0, false,
                    null, null, null, null);
            lista.add(new PedidoDetalle(0, null, producto, item.cantidad(), item.precio()));
        }
        return new Pedido(0, null, pedidoDto.estado(), usuario, pedidoDto.entrega(), direccion,
                pedidoDto.precio_envio(), pedidoDto.precio_cupon(), pedidoDto.total(), 0, pedidoDto.comentarios(),
                pedidoDto.fecha_entrega(), null, null, lista);
    }

    private static String generarNumeroPedido() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 12; i++) numero.append(secureRandom.nextInt(10));
        return numero.toString();
    }
}
