package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public ServiceResponse findByEmail(String email) {
        try {
            if (!usuarioRepository.existsByEmail(email))
                return new ServiceResponse("Email no existe", HttpStatus.BAD_REQUEST, null);
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(new Usuario());
            return new ServiceResponse("Usuario encontrado", HttpStatus.OK, entityToDto(usuario));
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private UsuarioDto entityToDto(Usuario usuario) {
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
                null
        );
    }
}
