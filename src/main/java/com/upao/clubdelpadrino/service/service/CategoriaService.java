package com.upao.clubdelpadrino.service.service;

import com.upao.clubdelpadrino.service.repository.CategoriaRepository;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.upao.clubdelpadrino.service.utils.Global.*;

@Service
@Transactional
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public GenericResponse listarCategoriasActivas(){
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, this.repository.listarCategoriasActivas());
    }
}