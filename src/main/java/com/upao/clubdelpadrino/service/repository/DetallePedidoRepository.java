package com.upao.clubdelpadrino.service.repository;

import com.upao.clubdelpadrino.service.entity.DetallePedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DetallePedidoRepository extends CrudRepository<DetallePedido, Integer> {

    @Query("SELECT DP FROM DetallePedido DP WHERE DP.pedido.id = :idPedido")
    Iterable<DetallePedido> findByPedido(int idPedido);

    @Query(value = "SELECT SUM(dp.cantidad * dp.precio) AS \"Total\" FROM detalle_pedido dp JOIN pedido p\n"
            + "ON p.id = dp.pedido_id\n"
            + "WHERE p.cliente_id = :idClient AND dp.pedido_id = :idPedido", nativeQuery = true)
    Double totalByIdCustomer(int idClient, int idPedido);
}