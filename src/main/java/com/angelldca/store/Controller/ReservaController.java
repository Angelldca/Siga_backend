package com.angelldca.store.Controller;

import com.angelldca.store.Enties.Reserva;
import com.angelldca.store.Enties.Usuario;
import com.angelldca.store.Helpers.FormatMesseage;
import com.angelldca.store.Service.ReservaService;
import com.angelldca.store.Service.UserService;
import com.angelldca.store.Service.dto.PlatoReservaDTO;
import com.angelldca.store.Service.dto.ReservaDto;
import com.angelldca.store.Service.dto.ReservaDtoMenu;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/reserva")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservaController {
    private final ReservaService reservaService;
    private final UserService userService;

    private FormatMesseage formatMesseage;

    public ReservaController(ReservaService reservaService, UserService userService) {
        this.reservaService = reservaService;
        this.userService = userService;

        this.formatMesseage = new FormatMesseage();
    }



    @PostMapping(value = "/createReserva")
    public ResponseEntity<Reserva> createReserva(@Valid @RequestBody ReservaDto reserva, BindingResult result ){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMesseage.formatMessage(result));
        }


        return reservaService.createReserva(reserva);
    }
    @GetMapping
    public List<Reserva> listarReservas(){
        return reservaService.listarReservas();
    }

    @GetMapping("/reservausuario/{id}")
    public List<ReservaDtoMenu> listarReservasUsuario(@PathVariable("id") Long id){

        Usuario usuario = userService.getUser(id);
        return reservaService.findByUsuarioReserva(usuario);
    }

    @GetMapping("/reservaId/{id}")
    public Reserva listarReservaById(@RequestParam(name = "id_reserva") Long id){
        return reservaService.getReserva(id);
    }
    //actualizar reserva
    @PutMapping(value = "/updateReserva/{id}")
    public ResponseEntity<Reserva> updateReserva(@Valid @PathVariable("id") Long id, @RequestBody  ReservaDto reserva, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMesseage.formatMessage(result));
        }
        return reservaService.actualizarReserva(reserva,id);
    }
    //Eliminar menu
    @DeleteMapping(value = "/deleteReserva/")
    public ResponseEntity<Reserva> deleteUser (@RequestParam(name = "id", required = true) Long id){

        return reservaService.deleteReservaAdmin(id);
    }

    /// reservas por fecha y plato
    @PostMapping(value = "/reservasFechaPlato")
    public List<Integer> reservasDiasMes(@Valid @RequestBody PlatoReservaDTO platoReservaDTO){
         List<Integer> reservas = reservaService.reservasDias(platoReservaDTO.getFechaInicio(),
                 platoReservaDTO.getFechaFin(),
                 platoReservaDTO.getId_plato());
       return reservas;
    }

}
