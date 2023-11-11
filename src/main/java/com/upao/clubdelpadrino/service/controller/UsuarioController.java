package com.upao.clubdelpadrino.service.controller;

import com.upao.clubdelpadrino.service.entity.Usuario;
import com.upao.clubdelpadrino.service.service.UsuarioService;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public GenericResponse<Usuario> login(HttpServletRequest request){
        String email = request.getParameter("email");
        String contrasena = request.getParameter("pass");
        return this.service.login(email, contrasena);
    }
}
