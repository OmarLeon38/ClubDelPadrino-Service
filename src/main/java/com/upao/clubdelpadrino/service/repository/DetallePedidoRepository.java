package com.upao.clubdelpadrino.service.repository;

import com.upao.clubdelpadrino.service.entity.DetallePedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DetallePedidoRepository extends CrudRepository<DetallePedido, Integer> {

    @Query("SELECT DP FROM DetallePedido DP WHERE DP.pedido.id = :idPedido")
    Iterable<DetallePedido> findByPedido(int idPedido);

}
