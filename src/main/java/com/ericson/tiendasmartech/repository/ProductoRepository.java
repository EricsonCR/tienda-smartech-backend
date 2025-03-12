package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByNombre(String nombre);

    Optional<Producto> findByNombre(String nombre);
}
