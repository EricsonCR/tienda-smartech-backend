package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.OficinaDto;
import com.ericson.tiendasmartech.entity.Direccion;
import com.ericson.tiendasmartech.entity.Oficina;
import com.ericson.tiendasmartech.mapper.OficinaMapper;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.DireccionRepository;
import com.ericson.tiendasmartech.repository.OficinaRepository;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.OficinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OficinaServiceImpl implements OficinaService {
    private final OficinaRepository oficinaRepository;
    private final OficinaMapper oficinaMapper;
    private final UsuarioRepository usuarioRepository;
    private final DireccionRepository direccionRepository;

    @Override
    public ServiceResponse listAll() {
        try {
            List<Oficina> lista = oficinaRepository.findAll();
            return new ServiceResponse("Office list all", HttpStatus.OK, oficinaMapper.toListDto(lista));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse findById(String id) {
        return null;
    }

    @Override
    public ServiceResponse create(OficinaDto oficinaDto) {
        try {
            Oficina oficina = oficinaMapper.toEntity(oficinaDto);
            if (!usuarioRepository.existsById(oficina.getUsuario().getId()))
                return new ServiceResponse("User not exists", HttpStatus.BAD_REQUEST, null);
            Direccion direccion = direccionRepository.save(oficina.getDireccion());
            oficina.setDireccion(direccion);
            oficinaRepository.save(oficina);
            return new ServiceResponse("Oficina create", HttpStatus.OK, oficinaMapper.toDto(oficina));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse update(OficinaDto oficinaDto) {
        try {
            if (!oficinaRepository.existsById(oficinaDto.id()))
                return new ServiceResponse("Office not exists", HttpStatus.BAD_REQUEST, null);
            if (!usuarioRepository.existsById(oficinaDto.usuario().id()))
                return new ServiceResponse("User not exists", HttpStatus.BAD_REQUEST, null);
            if (!direccionRepository.existsById(oficinaDto.direccion().id()))
                return new ServiceResponse("Direction not exists", HttpStatus.BAD_REQUEST, null);
            Oficina oficina = oficinaMapper.toEntity(oficinaDto);
            Direccion direccion = direccionRepository.save(oficina.getDireccion());
            oficina.setDireccion(direccion);
            oficinaRepository.save(oficina);
            return new ServiceResponse("Office update", HttpStatus.OK, oficinaMapper.toDto(oficina));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse delete(long id) {
        return null;
    }
}
