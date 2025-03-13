package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNumero(String numero);

    boolean existsByEmail(String email);

    boolean existsByNumero(String numero);

    boolean existsByEmailAndVerificadoIsTrue(String email);
}
