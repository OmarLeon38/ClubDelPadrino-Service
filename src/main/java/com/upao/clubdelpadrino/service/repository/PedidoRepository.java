package com.upao.clubdelpadrino.service.repository;

import com.upao.clubdelpadrino.service.entity.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

    @Query("SELECT P FROM Pedido P WHERE P.cliente.id = :idClient")
    Iterable<Pedido> obtenerMisCompras(int idClient);

//    @Query("SELECT P FROM Pedido P WHERE P.id=:idOrden AND P.cliente.id=:idClient")
//    Optional<Pedido> findByIdAndClienteId( int idClient, int idOrden);
}
