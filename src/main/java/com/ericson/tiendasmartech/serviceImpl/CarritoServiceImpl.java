package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CarritoRepository;
import com.ericson.tiendasmartech.repository.ProductoRepository;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public ServiceResponse registrar(CarritoDto carritoDto) {
        try {
            Carrito carrito = dtoToEntity(carritoDto);
            if (carrito.getUsuario().getId() == 0)
                return new ServiceResponse("El carrito no tiene usuario asignado",
                        HttpStatus.BAD_REQUEST, null);
            for (CarritoDetalle carritoDetalle : carrito.getCarritoDetalles())
                carritoDetalle.setCarrito(carrito);
            carritoRepository.save(carrito);
            return new ServiceResponse("Carrito agregado exitosamente", HttpStatus.OK,
                    entityToDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse agregarItem(long id, CarritoDetalleDto carritoDetalleDto) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null)
                return new ServiceResponse("Carrito no encontrado", HttpStatus.BAD_REQUEST, null);
            if (existsItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles()))
                return new ServiceResponse("Producto ya existe", HttpStatus.FOUND, null);
            carrito.getCarritoDetalles().add(dtoToEntity(carritoDetalleDto));
            carrito.getCarritoDetalles().getLast().setCarrito(carrito);
            carritoRepository.save(carrito);
            return new ServiceResponse("Producto agregado al carrito", HttpStatus.OK,
                    entityToDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse eliminarItem(long id, CarritoDetalleDto carritoDetalleDto) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null)
                return new ServiceResponse("Carrito no encontrado", HttpStatus.BAD_REQUEST, null);
            int index = indexItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles());
            if (index == -1)
                return new ServiceResponse("El producto no existe", HttpStatus.BAD_REQUEST, null);
            carrito.getCarritoDetalles().remove(index);
            carritoRepository.save(carrito);
            return new ServiceResponse("Producto eliminado", HttpStatus.OK, entityToDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse sumarItem(long id, CarritoDetalleDto carritoDetalleDto) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null)
                return new ServiceResponse("Carrito no encontrado", HttpStatus.BAD_REQUEST, null);
            int index = indexItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles());
            if (index == -1)
                return new ServiceResponse("El producto no existe", HttpStatus.BAD_REQUEST, null);
            int cantidad = carrito.getCarritoDetalles().get(index).getCantidad();
            carrito.getCarritoDetalles().get(index).setCantidad(++cantidad);
            carritoRepository.save(carrito);
            return new ServiceResponse("Producto aumentado", HttpStatus.OK, entityToDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse restarItem(long id, CarritoDetalleDto carritoDetalleDto) {
        try {
            Carrito carrito = carritoRepository.findById(id).orElse(null);
            if (carrito == null)
                return new ServiceResponse("Carrito no encontrado", HttpStatus.BAD_REQUEST, null);
            int index = indexItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles());
            if (index == -1)
                return new ServiceResponse("El producto no existe", HttpStatus.BAD_REQUEST, null);
            int cantidad = carrito.getCarritoDetalles().get(index).getCantidad();
            if (cantidad > 1) carrito.getCarritoDetalles().get(index).setCantidad(--cantidad);
            else return new ServiceResponse("La cantidad minima debe ser 2", HttpStatus.BAD_REQUEST,
                    null);
            carritoRepository.save(carrito);
            return new ServiceResponse("Producto disminuido", HttpStatus.OK, entityToDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse buscarPorUsuario(long id) {
        try {
            Carrito carrito = carritoRepository.findByUsuario(id).orElse(new Carrito());
            if (carrito.getUsuario() == null)
                return new ServiceResponse("No exsite carrito para este usuario", HttpStatus.BAD_REQUEST, null);
            return new ServiceResponse("Carrito encontrado", HttpStatus.OK, entityToDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse actualizar(CarritoDto carritoDto) {
        try {
            Carrito carrito = carritoRepository.findByUsuario(carritoDto.usuario().id()).orElse(new Carrito());
            if (carrito.getId() == 0)
                return new ServiceResponse("No exite carrito", HttpStatus.BAD_REQUEST, null);
            List<CarritoDetalle> lista = carrito.getCarritoDetalles();
            for (CarritoDetalleDto carritoDetalleDto : carritoDto.carritoDetalles()) {
                if (!existsItem(carritoDetalleDto.producto().id(), carrito.getCarritoDetalles()))
                    lista.add(dtoToEntity(carritoDetalleDto));
            }
            carrito.setCarritoDetalles(lista);
            carrito.getCarritoDetalles().forEach(detalle -> detalle.setCarrito(carrito));
            carritoRepository.save(carrito);
            return new ServiceResponse("Carrito acoplado exitosamente", HttpStatus.OK,
                    entityToDto(carrito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private int indexItem(long id, List<CarritoDetalle> detalles) {
        return IntStream.range(0, detalles.size())
                .filter(i -> detalles.get(i).getProducto().getId() == id)
                .findFirst()
                .orElse(-1); // Retorna -1 si no encuentra el elemento
    }

    private boolean existsItem(long id, List<CarritoDetalle> detalles) {
        for (CarritoDetalle carritoDetalle : detalles) {
            if (carritoDetalle.getProducto().getId() == id) return true;
        }
        return false;
    }

    private CarritoDetalle dtoToEntity(CarritoDetalleDto carritoDetalleDto) {
        ProductoDto p = carritoDetalleDto.producto();
        List<Foto> fotos = new ArrayList<>();
        for (FotoDto fotoDto : p.fotos()) {
            fotos.add(new Foto(fotoDto.id(),
                    new Galeria(fotoDto.galeria().id(), fotoDto.galeria().url()), null));
        }
        Producto producto = new Producto(p.id(), p.sku(), p.nombre(), p.descripcion(), p.slogan(),
                new Marca(p.marca().id(), p.marca().nombre(), null, null),
                new Categoria(p.categoria().id(), p.categoria().nombre(), null, null), p.precio(),
                p.descuento(),
                p.stock(), false, null, null, fotos, null);
        return new CarritoDetalle(carritoDetalleDto.id(), null, producto,
                carritoDetalleDto.cantidad());
    }

    private CarritoDto entityToDto(Carrito carrito) {
        List<CarritoDetalleDto> listaCarritoDetalleDtos = new ArrayList<>();
        for (CarritoDetalle carritoDetalle : carrito.getCarritoDetalles()) {
            listaCarritoDetalleDtos
                    .add(new CarritoDetalleDto(carritoDetalle.getId(),
                            new CarritoDto(carrito.getId(), null, null),
                            entityToDto(carritoDetalle.getProducto()),
                            carritoDetalle.getCantidad()));
        }
        return new CarritoDto(carrito.getId(), entityToDto(carrito.getUsuario()),
                listaCarritoDetalleDtos);
    }

    private ProductoDto entityToDto(Producto producto) {
        CategoriaDto categoriaDto = new CategoriaDto(producto.getCategoria().getId(),
                producto.getCategoria().getNombre(), null);
        MarcaDto marcaDto = new MarcaDto(producto.getMarca().getId(),
                producto.getMarca().getNombre(), null);
        List<FotoDto> listaFotoDtos = new ArrayList<>();
        if (producto.getFotos() != null) {
            for (Foto foto : producto.getFotos()) {
                listaFotoDtos.add(new FotoDto(foto.getId(),
                        new GaleriaDto(foto.getGaleria().getId(), foto.getGaleria().getUrl())));
            }
        }
        return new ProductoDto(producto.getId(), producto.getSku(), producto.getNombre(),
                producto.getDescripcion(),
                producto.getSlogan(), marcaDto, categoriaDto, producto.getPrecio(),
                producto.getDescuento(),
                producto.getStock(), listaFotoDtos, null);
    }

    private UsuarioDto entityToDto(Usuario usuario) {
        return new UsuarioDto(usuario.getId(), usuario.getDocumento(), usuario.getNumero(),
                usuario.getRol(),
                usuario.getNombres(), usuario.getApellidos(), usuario.getDireccion(),
                usuario.getTelefono(),
                usuario.getEmail(), usuario.getNacimiento(), null, null);
    }

    private Carrito dtoToEntity(CarritoDto carritoDto) {
        Usuario usuario = usuarioRepository.findByEmail(carritoDto.usuario().email())
                .orElse(new Usuario());
        List<CarritoDetalle> listaCarritoDetalles = new ArrayList<>();
        for (CarritoDetalleDto carritoDetalleDto : carritoDto.carritoDetalles()) {
            Producto producto = productoRepository.findById(carritoDetalleDto.producto().id())
                    .orElse(new Producto());
            listaCarritoDetalles.add(
                    new CarritoDetalle(carritoDetalleDto.id(), new Carrito(), producto,
                            carritoDetalleDto.cantidad()));
        }
        return new Carrito(carritoDto.id(), usuario, listaCarritoDetalles, null, null);
    }

}