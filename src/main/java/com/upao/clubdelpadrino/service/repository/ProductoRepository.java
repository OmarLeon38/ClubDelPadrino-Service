package com.upao.clubdelpadrino.service.repository;

import com.upao.clubdelpadrino.service.entity.Producto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Integer>{

    @Query("SELECT P FROM Producto P WHERE P.recomendado IS TRUE")
    Iterable<Producto> listarProductosRecomendados();

    @Query("SELECT P FROM Producto P WHERE P.categoria.id=:idC")
    Iterable<Producto> listarProductosPorCategoria(int idC);

    @Modifying
    @Query("UPDATE Producto P SET P.stock=P.stock-:cant WHERE P.id=:id")
    void actalizarStock(int cant, int id);
}
