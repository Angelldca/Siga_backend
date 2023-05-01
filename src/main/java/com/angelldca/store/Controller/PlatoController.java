package com.angelldca.store.Controller;


import com.angelldca.store.Enties.Plato;
import com.angelldca.store.Helpers.FormatMesseage;
import com.angelldca.store.Service.PlatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/plato")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlatoController {

    private final  PlatoService platoService;

    private FormatMesseage formatMesseage;
    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
        this.formatMesseage  = new FormatMesseage();
    }
    /// crear plato
    @PostMapping(value = "/createPlato")
    public ResponseEntity<Plato> createPlato(@Valid @RequestBody Plato plato, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMesseage.formatMessage(result));
        }
        return platoService.createPlato(plato);
    }
    //lsitar platos
    @GetMapping
    public List<Plato> listarPlatos(){
        return platoService.listarPlatos();
    }
    //cantidad de platos
    @GetMapping("cantPlatos")
    public int cantPlatos(){
        return platoService.listarPlatos().size();
    }
    @GetMapping(value = "search/{nombre_plato}")
    public Plato listarPlatos( @PathVariable("nombre_plato") String nombre_plato){
        return platoService.findByNombre_plato(nombre_plato);
    }
    // actualizar platos
    @PutMapping(value = "/updatePlato/{id}")
    public ResponseEntity<Plato> actualizarPlato(@Valid  @PathVariable("id") Long id,@RequestBody Plato plato, BindingResult result){
        //Plato plato = platoService.getPlato(id);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMesseage.formatMessage(result));
        }

        return platoService.actualizarPlato(plato,id);
    }
    //eliminar platos
    @DeleteMapping(value = "deletePlato/")
    public ResponseEntity<Plato> eliminarPlato (@RequestParam(name = "id", required = true) Long id){

        return platoService.deletePlato(id);
    }

}
