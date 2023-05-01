package com.angelldca.store.Controller;



import com.angelldca.store.Enties.Usuario;
import com.angelldca.store.Helpers.FormatMesseage;

import com.angelldca.store.Service.UserService;

import com.angelldca.store.Service.dto.UsuarioDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

@RequestMapping(value = "user")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private FormatMesseage formatMesseage;



    public UserController(UserService userService) {
        this.userService = userService;
        this.formatMesseage = new FormatMesseage();
    }

    @PostMapping("/autenticate")
    public ResponseEntity<Usuario> autenticarUsuario(@RequestBody UsuarioDto user){
        return userService.autenticarUsuario(user.getUsername(),user.getPassword());
    }
    @PostMapping(value = "/createUser")
    public ResponseEntity<Usuario> createUser(@Valid @RequestBody Usuario user,
                                              BindingResult result ){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMesseage.formatMessage(result));
        }
        return userService.createUser(user);
    }
    @GetMapping
    public List<Usuario> listarUser(){
        return userService.listarUser();
    }
    //actualizar menu
    @PutMapping(value = "/updateUser/{id}")
    public ResponseEntity<Usuario> updateUser(@Valid @PathVariable("id") Long id, @RequestBody  Usuario user,BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMesseage.formatMessage(result));
        }
        return userService.actualizarUser(user,id);
    }
    //Eliminar menu
    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<Usuario> deleteUser (@PathVariable("id") Long id){

        return userService.deleteUser(id);
    }
    @PutMapping(value = "/deleteUserInactive/{id}")
    public ResponseEntity<Usuario> deleteUserEstado (@PathVariable("id") Long id){

        return userService.makeAsInactivo(id);
    }
}
