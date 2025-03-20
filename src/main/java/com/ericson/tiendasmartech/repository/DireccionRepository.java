package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    @Query(nativeQuery = true, value = "select * from direcciones d where d.usuario=?1")
    List<Direccion> findAllByUsuario(long id);
}
