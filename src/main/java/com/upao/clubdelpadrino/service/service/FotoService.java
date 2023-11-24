package com.upao.clubdelpadrino.service.service;

import com.upao.clubdelpadrino.service.entity.Foto;
import com.upao.clubdelpadrino.service.repository.FotoRepository;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import static com.upao.clubdelpadrino.service.utils.Global.*;

@Service
@Transactional
public class FotoService {
    private FotoRepository repo;
    private FileStorageService storageService;

    public FotoService(FotoRepository repo, FileStorageService storageService) {
        this.repo = repo;
        this.storageService = storageService;
    }

    public GenericResponse<Iterable<Foto>> list() {
        return new GenericResponse<Iterable<Foto>>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, repo.list());
    }


    public GenericResponse find(Long aLong) {
        return null;
    }


    public GenericResponse save(Foto obj) {
        String fileName = (repo.findById(obj.getId())).orElse(new Foto()).getFileName();

        String originalFilename = obj.getFile().getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        fileName = storageService.storeFile(obj.getFile(), fileName);

        obj.setFileName(fileName);
        obj.setExtension(extension);

        return new GenericResponse(TIPO_DATA, RPTA_OK,OPERACION_CORRECTA,repo.save(obj));
    }

    public ResponseEntity<Resource> download(String completefileName, HttpServletRequest request) {
        Resource resource = storageService.loadResource(completefileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public ResponseEntity<Resource> downloadByFileName(String fileName, HttpServletRequest request) {
        Foto doc = repo.findByFileName(fileName).orElse(new Foto());
        return download(doc.getCompleteFileName(), request);
    }

    public HashMap<String, Object> validate(Foto obj) {
        return null;
    }

    public GenericResponse deleteById(Long id) {
        boolean deleted = false;
        Optional<Foto> foto = repo.findById(id);
        if (foto.isPresent()) {
            deleted = storageService.deleteFile(foto.get().getCompleteFileName());
            int deletedFromBD = repo.deleteImageById(foto.get().getId());
            if (deletedFromBD == 1 && deleted) {
                return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, deleted);
            } else {
                return new GenericResponse(TIPO_DATA, RPTA_WARNING, OPERACION_ERRONEA, deleted);
            }
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_ERROR, "No se ha encontrado ninguna foto con ese Id", deleted);
        }
    }
}