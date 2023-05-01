package com.angelldca.store.Controller;


import com.angelldca.store.Enties.Rol;
import com.angelldca.store.Repository.RolRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "rol")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RolController {
    private final RolRepository rolRepository;

    public RolController(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }
    @GetMapping
    public List<Rol> listarRoles(){
        return rolRepository.findAll();
    }
}
