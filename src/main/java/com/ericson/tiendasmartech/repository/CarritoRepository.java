package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    @Query(nativeQuery = true, value = "select * from carritos c where c.usuario = ?1")
    Optional<Carrito> findByUsuario(long id);
}
