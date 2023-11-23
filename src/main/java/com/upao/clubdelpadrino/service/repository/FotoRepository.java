package com.upao.clubdelpadrino.service.repository;

import com.upao.clubdelpadrino.service.entity.Foto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FotoRepository extends CrudRepository<Foto, Long> {
    @Query("SELECT da FROM Foto da WHERE da.estado = 'A' AND da.eliminado = false")
    Iterable<Foto> list();

    @Query("SELECT da FROM Foto da WHERE da.fileName = :fileName AND da.estado = 'A' AND da.eliminado = false")
    Optional<Foto> findByFileName(String fileName);

    @Transactional
    @Modifying
    @Query("DELETE FROM Foto da WHERE da.id = :id")
    int deleteImageById(Long id);
}
