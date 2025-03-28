package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    boolean existsByUsuarioIdAndProductoId(long usuarioId, Long productoId);
}
