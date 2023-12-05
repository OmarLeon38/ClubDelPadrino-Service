package com.upao.clubdelpadrino.service.controller;

import com.upao.clubdelpadrino.service.service.CategoriaService;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/categoria", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse listarCategoriasActivas(){
        return this.service.listarCategoriasActivas();
    }
}
