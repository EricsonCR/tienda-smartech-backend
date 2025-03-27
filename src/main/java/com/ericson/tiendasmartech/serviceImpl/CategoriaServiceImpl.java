package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.*;
import com.ericson.tiendasmartech.entity.*;
import com.ericson.tiendasmartech.mapper.CategoriaMapper;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.repository.CategoriaRepository;
import com.ericson.tiendasmartech.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public ServiceResponse listar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDto> lista = new ArrayList<>();
        if (!categorias.isEmpty()) {
            for (Categoria categoria : categorias) {
                lista.add(categoriaMapper.toDto(categoria));
            }
            return new ServiceResponse("Lista de categorias", HttpStatus.OK, lista);
        }
        return new ServiceResponse("Lista vacia", HttpStatus.NOT_FOUND, null);
    }
}