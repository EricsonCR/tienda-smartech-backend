package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {
    @Query(nativeQuery = true, value = "select * from domicilios d where d.usuario = ?1")
    List<Domicilio> findAllByUsuario(long id);
}
