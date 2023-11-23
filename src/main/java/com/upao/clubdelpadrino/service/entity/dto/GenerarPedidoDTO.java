package com.upao.clubdelpadrino.service.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upao.clubdelpadrino.service.entity.Cliente;
import com.upao.clubdelpadrino.service.entity.DetallePedido;
import com.upao.clubdelpadrino.service.entity.Pedido;

public class GenerarPedidoDTO {
    private Pedido pedido;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Iterable<DetallePedido> detallePedido;
    private Cliente cliente;

    public GenerarPedidoDTO() {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
