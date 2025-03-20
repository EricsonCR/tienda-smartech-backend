package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.DomicilioDto;
import com.ericson.tiendasmartech.entity.Consignatario;
import com.ericson.tiendasmartech.entity.Direccion;
import com.ericson.tiendasmartech.entity.Domicilio;
import com.ericson.tiendasmartech.mapper.DomicilioMapper;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.ConsignatarioRepository;
import com.ericson.tiendasmartech.repository.DireccionRepository;
import com.ericson.tiendasmartech.repository.DomicilioRepository;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.DomicilioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomicilioServiceImpl implements DomicilioService {

    private final DomicilioRepository domicilioRepository;
    private final DireccionRepository direccionRepository;
    private final ConsignatarioRepository consignatarioRepository;
    private final DomicilioMapper domicilioMapper;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ServiceResponse listAllByUser(long id) {
        try {
            List<Domicilio> domicilios = domicilioRepository.findAllByUsuario(id);
            return new ServiceResponse("Domicilios found", HttpStatus.OK, domicilioMapper.toListDto(domicilios));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse findById(long id) {
        try {
            Domicilio domicilio = domicilioRepository.findById(id).orElse(null);
            if (domicilio == null) return new ServiceResponse("ID no existe", HttpStatus.BAD_REQUEST, null);
            return new ServiceResponse("Domicilio found", HttpStatus.OK, domicilioMapper.toDto(domicilio));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse save(DomicilioDto domicilioDto) {
        try {
            Domicilio domicilio = domicilioMapper.toEntity(domicilioDto);
            Direccion direccion = direccionRepository.save(domicilio.getDireccion());
            Consignatario consignatario = consignatarioRepository.save(domicilio.getConsignatario());
            domicilio.setDireccion(direccion);
            domicilio.setConsignatario(consignatario);
            domicilioRepository.save(domicilio);
            return new ServiceResponse("Domicilio save", HttpStatus.OK, domicilioMapper.toDto(domicilio));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse update(DomicilioDto domicilioDto) {
        try {
            if (!domicilioRepository.existsById(domicilioDto.id()))
                return new ServiceResponse("ID domicilio no existe", HttpStatus.BAD_REQUEST, null);
            Domicilio domicilio = domicilioMapper.toEntity(domicilioDto);
            if (!consignatarioRepository.existsById(domicilio.getConsignatario().getId()))
                return new ServiceResponse("ID consignatario no existe", HttpStatus.BAD_REQUEST, null);
            if (!direccionRepository.existsById(domicilio.getDireccion().getId()))
                return new ServiceResponse("ID direccion no existe", HttpStatus.BAD_REQUEST, null);
            if (!usuarioRepository.existsById(domicilio.getUsuario().getId()))
                return new ServiceResponse("ID usuario no existe", HttpStatus.BAD_REQUEST, null);
            domicilioRepository.save(domicilio);
            return new ServiceResponse("Domicilio update", HttpStatus.OK, domicilioMapper.toDto(domicilio));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse delete(long id) {
        try {
            if (!domicilioRepository.existsById(id))
                return new ServiceResponse("ID domicilio no existe", HttpStatus.BAD_REQUEST, null);
            domicilioRepository.deleteById(id);
            return new ServiceResponse("Domicilio delete", HttpStatus.OK, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
