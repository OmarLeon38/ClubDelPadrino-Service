package com.upao.clubdelpadrino.service.service;

import com.upao.clubdelpadrino.service.entity.Cliente;
import com.upao.clubdelpadrino.service.repository.ClienteRepository;
import com.upao.clubdelpadrino.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.upao.clubdelpadrino.service.utils.Global.*;

@Service
@Transactional
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    //Método guardar y actualizar cliente
    public GenericResponse save(Cliente c){
        Optional<Cliente> opt = this.repository.findById(c .getId());
        int idf = opt.isPresent() ? opt.get().getId() : 0;
        if (idf == 0){
            if (repository.existByDoc(c.getNumDoc().trim()) == 1){
                return new GenericResponse(TIPO_RESULT, RPTA_WARNING, "Error: Ya existe un cliente con ese número de DNI", null);
            }else {
                //Guarda
                c.setId(idf);
                return new GenericResponse(TIPO_DATA, RPTA_OK, "Cliente registrado correctamente", this.repository.save(c));
            }
        }else {
            //Actualizar registro
            if (repository.existByDocForUpdate(c.getNumDoc().trim(), c.getId()) == 1){
                return new GenericResponse(TIPO_RESULT, RPTA_WARNING, "Error: Ya existe un cliente con esas credenciales", null);
            }else {
                //Actualiza
                c.setId(idf);
                return new GenericResponse(TIPO_DATA, RPTA_OK, "Datos del cliente actualizados", this.repository.save(c));
            }
        }
    }
}
