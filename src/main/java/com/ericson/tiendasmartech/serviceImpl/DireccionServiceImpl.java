package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.DireccionDto;
import com.ericson.tiendasmartech.entity.Direccion;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.DireccionRepository;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.DireccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ServiceResponse listarPorUsuario(long id) {
        try {
            List<Direccion> direcciones = direccionRepository.findAllByUsuario(id);
            if (direcciones.isEmpty())
                return new ServiceResponse("Lista vacia", HttpStatus.NOT_FOUND, null);
            return new ServiceResponse("Lista de direcciones", HttpStatus.OK,
                    entityToDto(direcciones));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse buscarPorId(long id) {
        try {
            Direccion direccion = direccionRepository.findById(id).orElse(null);
            if (direccion == null)
                return new ServiceResponse("Direccion no encontrada", HttpStatus.NOT_FOUND, null);
            return new ServiceResponse("Direccion encontrado exitosamente", HttpStatus.OK,
                    entityToDto(direccion));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse registrar(DireccionDto direccionDto) {
        try {
            if (!usuarioRepository.existsById(direccionDto.id()))
                return new ServiceResponse("Usuario no existe", HttpStatus.BAD_REQUEST, null);
            direccionRepository.save(dtoToEntity(direccionDto));
            return new ServiceResponse("Direccion registrada exitosamente", HttpStatus.OK, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse actualizar(DireccionDto direccionDto) {
        try {
            if (!direccionRepository.existsById(direccionDto.id()))
                return new ServiceResponse("Direccion no existe", HttpStatus.BAD_REQUEST, null);
            if (!usuarioRepository.existsById(direccionDto.id()))
                return new ServiceResponse("Usuario no existe", HttpStatus.BAD_REQUEST, null);
            direccionRepository.save(dtoToEntity(direccionDto));
            return new ServiceResponse("Direeccion actualiza exitosamente", HttpStatus.OK, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse eliminar(long id) {
        try {
            if (!direccionRepository.existsById(id))
                return new ServiceResponse("Direccion no existe", HttpStatus.BAD_REQUEST, null);
            direccionRepository.deleteById(id);
            return new ServiceResponse("Direccion eliminada exitosamente", HttpStatus.OK, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private List<DireccionDto> entityToDto(List<Direccion> direcciones) {
        List<DireccionDto> lista = new ArrayList<>();
        for (Direccion direccion : direcciones) {
            lista.add(entityToDto(direccion));
        }
        return lista;
    }

    private DireccionDto entityToDto(Direccion direccion) {
        return new DireccionDto(
                direccion.getId(),
                direccion.getVia(),
                direccion.getNombre(),
                direccion.getNumero(),
                direccion.getReferencia(),
                direccion.getDistrito(),
                direccion.getProvincia(),
                direccion.getDepartamento(),
                direccion.getCodigo_postal());
    }

    private Direccion dtoToEntity(DireccionDto direccionDto) {
        return new Direccion(
                direccionDto.id(),
                direccionDto.via(),
                direccionDto.nombre(),
                direccionDto.numero(),
                direccionDto.referencia(),
                direccionDto.distrito(),
                direccionDto.provincia(),
                direccionDto.departamento(),
                direccionDto.codigo_postal(),
                null,
                null);
    }
}