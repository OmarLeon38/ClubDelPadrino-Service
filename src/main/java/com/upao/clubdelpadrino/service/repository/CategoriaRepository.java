package com.upao.clubdelpadrino.service.repository;

import com.upao.clubdelpadrino.service.entity.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {

    @Query("SELECT C FROM Categoria C WHERE C.vigencia IS TRUE")
    Iterable<Categoria> listarCategoriasActivas();
}
