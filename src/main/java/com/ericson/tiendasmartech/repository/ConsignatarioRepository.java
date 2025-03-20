package com.ericson.tiendasmartech.repository;

import com.ericson.tiendasmartech.entity.Consignatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsignatarioRepository extends JpaRepository<Consignatario, Long> {
}
