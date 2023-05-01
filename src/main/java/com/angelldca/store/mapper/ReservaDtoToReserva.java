package com.angelldca.store.mapper;


import com.angelldca.store.Enties.Reserva;
import com.angelldca.store.Service.PlatoService;
import com.angelldca.store.Service.dto.ReservaDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ReservaDtoToReserva implements IMapper <ReservaDto, Reserva>{


    private final PlatoService platoService;

    public ReservaDtoToReserva(PlatoService platoService) {
        this.platoService = platoService;
    }
    public double precio(ReservaDto in){

        double precio= 0;
        for(int i=0;i< in.getId_platosMenu().length;i++){
            precio += platoService.getPlato(in.getId_platosMenu()[i]).getPrecio_plato();
        }

        return precio;
    }
    @Override
    public Reserva map(ReservaDto in) {
        Reserva r = new Reserva();
        r.setMenu(in.getMenu());
        r.setId_platosMenu(in.getId_platosMenu());
        r.setUsuarioReserva(in.getUsuarioReserva());
        r.setEstado("CREADA");
        r.setFecha_reserva(in.getMenu().getFecha());
        r.setPrecio(this.precio(in));
        return r;
    }
}