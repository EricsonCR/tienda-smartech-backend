package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.dto.EmailDto;
import com.ericson.tiendasmartech.entity.Usuario;
import com.ericson.tiendasmartech.enums.Rol;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.UsuarioRepository;
import com.ericson.tiendasmartech.service.AuthService;
import com.ericson.tiendasmartech.service.JwtService;
import com.ericson.tiendasmartech.service.EmailService;
import com.ericson.tiendasmartech.util.AuthUtil;
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
    private final EmailService emailService;
    private final AuthUtil authUtil;

    @Override
    public ServiceResponse signin(AuthDto authDto) {
        try {
            if (!usuarioRepository.existsByEmail(authDto.email()))
                return new ServiceResponse("Email no existe", HttpStatus.BAD_REQUEST, null);

            if (!usuarioRepository.existsByEmailAndVerificadoIsTrue(authDto.email()))
                return new ServiceResponse("Email no esta validado", HttpStatus.CONFLICT, null);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.email());
            String token = jwtService.getToken(userDetails);

            return new ServiceResponse("Usuario autenticado exitosamente railway", HttpStatus.OK, token);
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

            EmailDto emailDto = authUtil.generarEmailRegistro(authDto.email(), passwordEncoder.encode(authDto.email()));
//            if (!emailService.sendEmail(emailDto))
//                return new ServiceResponse("Usuario registrado, error email", HttpStatus.OK, null);
            return new ServiceResponse("Usuario registrado", HttpStatus.OK, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse validatedToken(String token) {
        try {

            String email = jwtService.getUsernameToken(token);
            if (!usuarioRepository.existsByEmail(email))
                return new ServiceResponse("Email no existe", HttpStatus.BAD_REQUEST, null);
            if (usuarioRepository.existsByEmailAndVerificadoIsTrue(email))
                return new ServiceResponse("Email ya esta validado", HttpStatus.CONFLICT, null);
            if (jwtService.expiredToken(token)) {
                String message = "Token expirado, debe generar token de registro";
                return new ServiceResponse(message, HttpStatus.BAD_REQUEST, null);
            }

            Usuario usuario = usuarioRepository.findByEmail(email).orElse(new Usuario());
            usuario.setVerificado(true);
            usuarioRepository.save(usuario);
            return new ServiceResponse("Usuario validado exitosamente", HttpStatus.OK, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse sendTokenSignup(String email) {
        try {
            if (!usuarioRepository.existsByEmail(email))
                return new ServiceResponse("Email no existe", HttpStatus.BAD_REQUEST, null);
            if (usuarioRepository.existsByEmailAndVerificadoIsTrue(email))
                return new ServiceResponse("Email ya esta validado", HttpStatus.BAD_REQUEST, null);

            EmailDto emailDto = authUtil.generarEmailRegistro(email, passwordEncoder.encode(email));
            if (emailService.sendEmail(emailDto))
                return new ServiceResponse("Email enviado", HttpStatus.OK, null);
            return new ServiceResponse("Email fallido", HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            return new ServiceResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ServiceResponse sendTokenForgot(String email) {
        return null;
    }

    private Usuario dtoToEntity(AuthDto authDto) {
        return new Usuario(0, authDto.documento(), authDto.numero(), Rol.CLIENTE, authDto.nombres(), authDto.apellidos(), null, null, authDto.email(), authDto.password(), false, false, null, null, null, null, null, null);
    }
}
