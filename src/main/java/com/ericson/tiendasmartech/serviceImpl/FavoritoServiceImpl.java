package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.FavoritoDto;
import com.ericson.tiendasmartech.entity.Favorito;
import com.ericson.tiendasmartech.mapper.FavoritoMapper;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.FavoritoRepository;
import com.ericson.tiendasmartech.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritoServiceImpl implements FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final FavoritoMapper favoritoMapper;

    @Override
    public ServiceResponse findAll() {
        return null;
    }

    @Override
    public ServiceResponse findAllByUsuario(long id) {
        return null;
    }

    @Override
    public ServiceResponse findById(long id) {
        return null;
    }

    @Override
    public ServiceResponse create(FavoritoDto favoritoDto) {
        try {
            Favorito favorito = favoritoMapper.toEntity(favoritoDto);
            long usuarioId = favorito.getUsuario().getId();
            long productoId = favorito.getProducto().getId();
            if (favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId))
                return new ServiceResponse("Favorito already exists", HttpStatus.BAD_REQUEST, null);
            favoritoRepository.save(favorito);
            return new ServiceResponse("Favorito create", HttpStatus.OK, favoritoMapper.toDto(favorito));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse update(FavoritoDto favoritoDto) {
        return null;
    }

    @Override
    public ServiceResponse delete(long id) {
        try {
            if (favoritoRepository.existsById(id)) {
                favoritoRepository.deleteById(id);
                return new ServiceResponse("Favorito deleted", HttpStatus.OK, null);
            }
            return new ServiceResponse("Favorito not found", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
