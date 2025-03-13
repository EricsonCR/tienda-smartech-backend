package com.ericson.tiendasmartech.ServiceImpl;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.enums.Rol;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.AuthService;
import com.ericson.tiendasmartech.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public ServiceResponse signin(AuthDto authDto) {
        try {
            if (!usuarioRepository.existsByEmail(authDto.email()))
                return new ServiceResponse("Email no existe", HttpStatus.BAD_REQUEST, null);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.email());
            String token = jwtService.getToken(userDetails);

            return new ServiceResponse("Usuario autenticado exitosamente", HttpStatus.OK, token);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse signup(AuthDto authDto) {
        try {
            if (usuarioRepository.existsByEmail(authDto.email()))
                return new ServiceResponse("Email ya existe", HttpStatus.BAD_REQUEST, null);
            if (usuarioRepository.existsByNumero(authDto.numero()))
                return new ServiceResponse("Numero ya existe", HttpStatus.BAD_REQUEST, null);
            Usuario usuario = dtoToEntity(authDto);
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioRepository.save(usuario);
            return new ServiceResponse("Usuario registrado", HttpStatus.OK, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse validatedToken(String token) {
        return null;
    }

    @Override
    public ServiceResponse sendTokenSignup(String email) {
        return null;
    }

    @Override
    public ServiceResponse sendTokenForgot(String email) {
        return null;
    }

    private Usuario dtoToEntity(AuthDto authDto) {
        return new Usuario(
                0,
                authDto.documento(),
                authDto.numero(),
                Rol.CLIENTE,
                authDto.nombres(),
                authDto.apellidos(),
                null,
                null,
                authDto.email(),
                authDto.password(),
                false,
                false,
                null,
                null,
                null,
                null
        );
    }
}
