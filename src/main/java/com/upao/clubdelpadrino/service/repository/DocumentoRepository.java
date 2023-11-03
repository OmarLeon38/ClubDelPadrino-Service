package com.upao.clubdelpadrino.service.repository;

import com.upao.clubdelpadrino.service.entity.Documento;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface DocumentoRepository extends CrudRepository<Documento, Long> {
    @Query("SELECT da FROM Documento da WHERE da.estado = 'A' AND da.eliminado = false")
    Iterable<Documento> list();

    @Query("SELECT da FROM Documento da WHERE da.fileName = :fileName AND da.estado = 'A' AND da.eliminado = false")
    Optional<Documento> findByFileName(String fileName);

    @Transactional
    @Modifying
    @Query("DELETE FROM Documento da WHERE da.id = :id")
    int deleteImageById(Long id);
}
