package com.upao.clubdelpadrino.service.service;

import com.upao.clubdelpadrino.service.entity.Usuario;
import com.upao.clubdelpadrino.service.repository.UsuarioRepository;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.upao.clubdelpadrino.service.utils.Global.*;

@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public GenericResponse<Usuario> login(String email, String contrasena){
        Optional<Usuario> optU = this.repository.login(email, contrasena);
        if(optU.isPresent()){
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_OK, "Sesión iniciada", optU.get());
        }
        else{
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_WARNING, "El usuario no existe", new Usuario());
        }
    }

    public GenericResponse guardarUsuario(Usuario u){
        Optional<Usuario> optU = this.repository.findById(u.getId());
        int idf = optU.isPresent() ? optU.get().getId() : 0;
        if (idf == 0){
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Usuario registrado con éxito", this.repository.save(u));
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Datos actualizados", this.repository.save(u));
        }
    }
}