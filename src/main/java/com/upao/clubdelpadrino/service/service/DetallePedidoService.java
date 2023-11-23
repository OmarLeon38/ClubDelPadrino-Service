package com.upao.clubdelpadrino.service.service;

import com.upao.clubdelpadrino.service.entity.DetallePedido;
import com.upao.clubdelpadrino.service.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DetallePedidoService {
    private final DetallePedidoRepository repository;

    public DetallePedidoService(DetallePedidoRepository repository) {
        this.repository = repository;
    }

    public void guardarDetalles(Iterable<DetallePedido> detalle){
        this.repository.saveAll(detalle);
    }
}
