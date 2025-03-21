package com.ericson.tiendasmartech.mapper;

import com.ericson.tiendasmartech.dto.DireccionDto;
import com.ericson.tiendasmartech.dto.OficinaDto;
import com.ericson.tiendasmartech.dto.UsuarioDto;
import com.ericson.tiendasmartech.entity.Direccion;
import com.ericson.tiendasmartech.entity.Oficina;
import com.ericson.tiendasmartech.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OficinaMapper {
    public OficinaDto toDto(Oficina oficina) {
        return new OficinaDto(
                oficina.getId(),
                oficina.getNombre(),
                oficina.getCelular(),
                oficina.getHora_inicio(),
                oficina.getHora_fin(),
                toUsuarioDto(oficina.getUsuario()),
                toDireccionDto(oficina.getDireccion())
        );
    }

    public Oficina toEntity(OficinaDto oficina) {
        return new Oficina(
                oficina.id(),
                oficina.nombre(),
                oficina.celular(),
                toUsuario(oficina.usuario()),
                toDireccion(oficina.direccion()),
                oficina.hora_inicio(),
                oficina.hora_fin(),
                null,
                null
        );
    }

    public List<OficinaDto> toListDto(List<Oficina> oficinas) {
        List<OficinaDto> lista = new ArrayList<>();
        for (Oficina oficina : oficinas) lista.add(toDto(oficina));
        return lista;
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
                null
        );
    }

    private Direccion toDireccion(DireccionDto direccion) {
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

    private Usuario toUsuario(UsuarioDto usuario) {
        return new Usuario(
                usuario.id(),
                usuario.documento(),
                usuario.numero(),
                usuario.rol(),
                usuario.nombres(),
                usuario.apellidos(),
                usuario.direccion(),
                usuario.telefono(),
                usuario.email(),
                null,
                false,
                false,
                usuario.nacimiento(),
                null,
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
}
