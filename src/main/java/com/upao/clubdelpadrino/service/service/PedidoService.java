package com.upao.clubdelpadrino.service.service;

import com.upao.clubdelpadrino.service.entity.DetallePedido;
import com.upao.clubdelpadrino.service.entity.Pedido;
import com.upao.clubdelpadrino.service.entity.dto.GenerarPedidoDTO;
import com.upao.clubdelpadrino.service.entity.dto.PedidoDetalleDTO;
import com.upao.clubdelpadrino.service.repository.DetallePedidoRepository;
import com.upao.clubdelpadrino.service.repository.PedidoRepository;
import com.upao.clubdelpadrino.service.repository.ProductoRepository;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.upao.clubdelpadrino.service.utils.Global.*;

@Service
@Transactional
public class PedidoService {
    private final PedidoRepository repository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoService dpService;
    private final ProductoRepository pRepository;

    public PedidoService(PedidoRepository repository, DetallePedidoRepository detallePedidoRepository, DetallePedidoService dpService, ProductoRepository pRepository) {
        this.repository = repository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.dpService = dpService;
        this.pRepository = pRepository;
    }

    public GenericResponse<List<PedidoDetalleDTO>> obtenerMisCompras(int idClient){
        final List<PedidoDetalleDTO> dtos = new ArrayList<>();
        final Iterable<Pedido> pedidos = repository.obtenerMisCompras(idClient);
        pedidos.forEach(pedido -> {
            dtos.add(new PedidoDetalleDTO(pedido, detallePedidoRepository.findByPedido(pedido.getId())));
        });
        return new GenericResponse<>(OPERACION_CORRECTA, RPTA_OK, "Petición correcta", dtos);
    }

    public GenericResponse guardarPedido(GenerarPedidoDTO dto){
        Date date = new Date();
        dto.getPedido().setFechaCompra(new java.sql.Date(date.getTime()));
        dto.getPedido().setAnularPedido(false);
        dto.getPedido().setMonto(dto.getPedido().getMonto());
        dto.getPedido().setCliente(dto.getCliente());
        this.repository.save(dto.getPedido());
        for (DetallePedido dp: dto.getDetallePedido()) {
            dp.setPedido(dto.getPedido());
            this.pRepository.actalizarStock(dp.getCantidad(), dp.getProducto().getId());
        }
        this.dpService.guardarDetalles(dto.getDetallePedido());
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, dto);
    }

    public GenericResponse anularPedido(int id){
        Pedido p = this.repository.findById(id).orElse(new Pedido());
        if (p.getId() != 0){
            p.setAnularPedido(true);
            this.repository.save(p);
            return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, p);
        }else {
            return new GenericResponse(TIPO_DATA, RPTA_ERROR, OPERACION_INCORRECTA, "Pedido inválido");
        }
    }

//    @NotNull
//    public ResponseEntity<Resource> exportInvoice(int idClient, int idOrden) {
//        Optional<Pedido> optPedido = this.repository.findByIdAndClienteId(idClient, idOrden);


//    }
}