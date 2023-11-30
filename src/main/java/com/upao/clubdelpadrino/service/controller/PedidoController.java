package com.upao.clubdelpadrino.service.controller;

import com.upao.clubdelpadrino.service.entity.dto.GenerarPedidoDTO;
import com.upao.clubdelpadrino.service.entity.dto.PedidoDetalleDTO;
import com.upao.clubdelpadrino.service.service.PedidoService;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping("/misPedidos/{idClient}")
    public GenericResponse<List<PedidoDetalleDTO>> obtenerMisComprasDetalle(@PathVariable int idClient){
        return this.service.obtenerMisCompras(idClient);
    }

    @PostMapping
    public GenericResponse guardarPedido(@RequestBody GenerarPedidoDTO dto){
        return this.service.guardarPedido(dto);
    }

    @DeleteMapping("/{id}")
    public GenericResponse anularPedido(@PathVariable int id){
        return this.service.anularPedido(id);
    }
}