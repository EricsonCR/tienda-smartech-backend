package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.DireccionDto;
import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Direccion;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

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

    @Override
    public ServiceResponse update(UsuarioDto usuarioDto) {
        try {
            String email = usuarioDto.email();
            if (usuarioRepository.existsByEmail(email)) {
                String message = validar(usuarioDto);
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
                entityToDto(usuario.getDirecciones())
        );
    }

    private List<DireccionDto> entityToDto(List<Direccion> direcciones) {
        List<DireccionDto> direccionDtos = new ArrayList<>();
        for (Direccion direccion : direcciones) {
            direccionDtos.add(new DireccionDto(
                    direccion.getId(),
                    null,
                    direccion.getVia(),
                    direccion.getDocumento(),
                    direccion.getNumero(),
                    direccion.getNombres(),
                    direccion.getCelular(),
                    direccion.getDireccion(),
                    direccion.getReferencia(),
                    direccion.getDistrito(),
                    direccion.getProvincia(),
                    direccion.getDepartamento(),
                    direccion.getCodigo_postal()
            ));
        }
        return direccionDtos;
    }

    private String validar(UsuarioDto usuarioDto) {
        String patronLetras = "^(?=.*[a-zA-Z]{2})([A-Za-z]{2,}( [A-Za-z]{2,})*)$";
        String patronDocumento = "^\\d{8,12}$";
        String patronCelular = "^9\\d{8}$";
        if (!Pattern.compile(patronLetras).matcher(usuarioDto.nombres()).matches())
            return "Error en campo nombres, solo caracteres de la a-z (minuscula, mayuscula, espacio, cantidad minima 2)";

        if (!Pattern.compile(patronLetras).matcher(usuarioDto.apellidos()).matches())
            return "Error en campo apellidos, solo caracteres de la a-z (minuscula, mayuscula, espacio, cantidad minima 2)";

        if (!Pattern.compile(patronDocumento).matcher(usuarioDto.numero()).matches())
            return "Error en el campo numero documento, solo digitos (0-9, minimo 8 y maximo 12)";

        Calendar fechaValida = Calendar.getInstance();
        fechaValida.add(Calendar.YEAR, -18);
        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.setTime(usuarioDto.nacimiento());
        // !fechaNacimientoCal.equals(fechaActual)
        if (fechaNacimientoCal.after(fechaValida))
            return "Error en campo nacimiento, debe ser mayor a 18 años.";

        if (!Pattern.compile(patronCelular).matcher(usuarioDto.telefono()).matches())
            return "Error en el campo numero celular, solo digitos (0-9, cantidad 9 digitos y empezar por 9)";

        return "OK";
    }
}
