package com.upao.clubdelpadrino.service.controller;

import com.upao.clubdelpadrino.service.service.ProductoService;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/producto")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public GenericResponse listarProductosRecomendados(){
        return this.service.listarProductosRecomendados();
    }

    @GetMapping("/{idC}")
    public GenericResponse listarProductosPorCategoria(@PathVariable int idC){
        return this.service.listarProductosPorCategoria(idC);
    }
}
