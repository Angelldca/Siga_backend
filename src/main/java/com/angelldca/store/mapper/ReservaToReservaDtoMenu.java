package com.angelldca.store.mapper;

import com.angelldca.store.Enties.Plato;
import com.angelldca.store.Enties.Reserva;
import com.angelldca.store.Service.PlatoService;
import com.angelldca.store.Service.dto.ReservaDtoMenu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservaToReservaDtoMenu implements IMapper<Reserva, ReservaDtoMenu>{
    private final MenuDtoToMenuPlato mapper;
   private final PlatoService platoService;
    public ReservaToReservaDtoMenu(MenuDtoToMenuPlato mapper, PlatoService platoService) {
        this.mapper = mapper;
        this.platoService = platoService;
    }

    @Override
    public ReservaDtoMenu map(Reserva in) {
        List<Plato> p = new ArrayList<>();
        ReservaDtoMenu r = new ReservaDtoMenu();
        r.setId_reserva(in.getId_reserva());
        r.setUsuarioReserva(in.getUsuarioReserva());
      //  for(int i = 0;i< in.getId_platosMenu().length;i++){
        //    p.add(platoService.getPlato(in.getId_platosMenu()[i]));
       // }
       // r.setPlatos(p);
        r.setId_platosMenu(in.getId_platosMenu());
        r.setMenu(mapper.map(in.getMenu()));
        return r;
    }
}
