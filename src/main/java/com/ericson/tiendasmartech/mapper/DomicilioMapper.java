package com.ericson.tiendasmartech.mapper;

import com.ericson.tiendasmartech.dto.ConsignatarioDto;
import com.ericson.tiendasmartech.dto.DireccionDto;
import com.ericson.tiendasmartech.dto.DomicilioDto;
import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Consignatario;
import com.ericson.tiendasmartech.entity.Direccion;
import com.ericson.tiendasmartech.entity.Domicilio;
import com.ericson.tiendasmartech.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DomicilioMapper {

    public List<DomicilioDto> toListDto(List<Domicilio> domicilios) {
        List<DomicilioDto> lista = new ArrayList<>();
        for (Domicilio domicilio : domicilios) {
            lista.add(new DomicilioDto(domicilio.getId(), toConsignatarioDto(domicilio.getConsignatario()), toDireccionDto(domicilio.getDireccion()), null));
        }
        return lista;
    }

    public DomicilioDto toDto(Domicilio domicilio) {
        return new DomicilioDto(
                domicilio.getId(),
                toConsignatarioDto(domicilio.getConsignatario()),
                toDireccionDto(domicilio.getDireccion()),
                toUsuarioDto(domicilio.getUsuario())
        );
    }

    public Domicilio toEntity(DomicilioDto domicilio) {
        return new Domicilio(
                domicilio.id(),
                toConsignatarioEntity(domicilio.consignatario()),
                toDireccionEntity(domicilio.direccion()),
                toUsuarioEntity(domicilio.usuario()),
                null,
                null);
    }

    private UsuarioDto toUsuarioDto(Usuario usuario) {
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
                null,
                null,
                null
        );
    }

    private DireccionDto toDireccionDto(Direccion direccion) {
        return new DireccionDto(
                direccion.getId(),
                direccion.getVia(),
                direccion.getNombre(),
                direccion.getNumero(),
                direccion.getReferencia(),
                direccion.getDistrito(),
                direccion.getProvincia(),
                direccion.getDepartamento(),
                direccion.getCodigo_postal()
        );
    }

    private ConsignatarioDto toConsignatarioDto(Consignatario consignatario) {
        return new ConsignatarioDto(
                consignatario.getId(),
                consignatario.getDocumento(),
                consignatario.getNumero(),
                consignatario.getNombres(),
                consignatario.getCelular(),
                consignatario.getEmail()
        );
    }

    private Usuario toUsuarioEntity(UsuarioDto usuario) {
        return new Usuario(
                usuario.id(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                false,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Direccion toDireccionEntity(DireccionDto direccion) {
        return new Direccion(
                direccion.id(),
                direccion.via(),
                direccion.nombre(),
                direccion.numero(),
                direccion.referencia(),
                direccion.distrito(),
                direccion.provincia(),
                direccion.departamento(),
                direccion.codigo_postal(),
                null,
                null
        );
    }

    private Consignatario toConsignatarioEntity(ConsignatarioDto consignatario) {
        return new Consignatario(
                consignatario.id(),
                consignatario.documento(),
                consignatario.numero(),
                consignatario.nombres(),
                consignatario.celular(),
                consignatario.email(),
                null,
                null
        );
    }
}
