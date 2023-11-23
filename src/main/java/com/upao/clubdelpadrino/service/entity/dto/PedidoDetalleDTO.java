package com.upao.clubdelpadrino.service.entity.dto;

import com.upao.clubdelpadrino.service.entity.DetallePedido;
import com.upao.clubdelpadrino.service.entity.Pedido;

import java.util.ArrayList;

public class PedidoDetalleDTO {
    private Pedido pedido;
    private Iterable<DetallePedido> detallePedido;

    public PedidoDetalleDTO() {
        this.pedido = new Pedido();
        this.detallePedido = new ArrayList<>();
    }

    public PedidoDetalleDTO(Pedido pedido, Iterable<DetallePedido> detallePedido) {
        this.pedido = pedido;
        this.detallePedido = detallePedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Iterable<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(Iterable<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }
}