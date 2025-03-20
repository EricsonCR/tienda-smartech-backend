package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.mapper.UsuarioMapper;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.UsuarioService;
import com.ericson.tiendasmartech.util.UsuarioUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioUtil usuarioUtil;

    @Override
    public ServiceResponse findByEmail(String email) {
        try {
            if (!usuarioRepository.existsByEmail(email))
                return new ServiceResponse("Email no existe", HttpStatus.BAD_REQUEST, null);
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(new Usuario());
            return new ServiceResponse("Usuario encontrado", HttpStatus.OK, usuarioMapper.toDto(usuario));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse update(UsuarioDto usuarioDto) {
        try {
            String email = usuarioDto.email();
            if (usuarioRepository.existsByEmail(email)) {
                String message = usuarioUtil.validar(usuarioDto);
                if (!message.equals("OK"))
                    return new ServiceResponse(message, HttpStatus.BAD_REQUEST, null);
                Usuario usuario = usuarioRepository.findByEmail(email).orElse(new Usuario());
                usuario.setDocumento(usuarioDto.documento());
                usuario.setNumero(usuarioDto.numero());
                usuario.setNombres(usuarioDto.nombres());
                usuario.setApellidos(usuarioDto.apellidos());
                usuario.setNacimiento(usuarioDto.nacimiento());
                usuario.setTelefono(usuarioDto.telefono());
                usuarioRepository.save(usuario);
                return new ServiceResponse("Usuario actualizado exitosamente", HttpStatus.OK, null);
            }
            return new ServiceResponse("El email no existe", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
