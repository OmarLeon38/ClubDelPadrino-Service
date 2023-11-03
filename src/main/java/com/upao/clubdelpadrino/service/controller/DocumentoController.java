package com.upao.clubdelpadrino.service.controller;

import com.upao.clubdelpadrino.service.entity.Documento;
import com.upao.clubdelpadrino.service.service.DocumentoService;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/documento")
public class DocumentoController {
    private DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @GetMapping
    public GenericResponse list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return service.downloadByFileName(fileName, request);
    }

    @PostMapping
    public GenericResponse save(@ModelAttribute Documento obj) {
        return service.save(obj);
    }

    public GenericResponse delete (Long aLong, Documento obj) {
        return null;
    }
    @PutMapping("/editImage/{id}")
    public GenericResponse update(@PathVariable Long id, @ModelAttribute Documento obj) {
        obj.setId(id);
        return service.save(obj);
    }

    @DeleteMapping("/deleteImage/{id}")
    public GenericResponse delete(@PathVariable Long id) {
        return service.deleteById(id);
    }
}