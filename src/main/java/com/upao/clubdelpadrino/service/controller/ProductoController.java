package com.upao.clubdelpadrino.service.controller;

import com.upao.clubdelpadrino.service.entity.Producto;
import com.upao.clubdelpadrino.service.service.ProductoService;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/producto", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public GenericResponse listarProductosRecomendados() {
        return this.service.listarProductosRecomendados();
    }

    @GetMapping("/{idC}")
    public GenericResponse listarProductosPorCategoria(@PathVariable int idC) {
        return this.service.listarProductosPorCategoria(idC);
    }

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) {
        return this.service.guardarProducto(producto);
    }
}
