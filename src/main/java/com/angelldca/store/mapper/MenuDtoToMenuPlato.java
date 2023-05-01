package com.angelldca.store.mapper;

import com.angelldca.store.Enties.Menu;
import com.angelldca.store.Enties.Plato;
import com.angelldca.store.Service.PlatoService;
import com.angelldca.store.Service.dto.MenuPlatosDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuDtoToMenuPlato implements IMapper<Menu, MenuPlatosDTO>{

    private final PlatoService platoService;

    public MenuDtoToMenuPlato(PlatoService platoService) {
        this.platoService = platoService;
    }

    @Override
    public MenuPlatosDTO map(Menu in) {

        List<Plato> p = new ArrayList<>();
        MenuPlatosDTO menuPlatosDTO = new MenuPlatosDTO();
        menuPlatosDTO.setId_menu(in.getId_menu());
        menuPlatosDTO.setPrecio(in.getPrecio());
        menuPlatosDTO.setEvento(in.getEvento());
        menuPlatosDTO.setFecha(in.getFecha());

        for(int i = 0;i< in.getId_plato().length;i++){
            p.add(platoService.getPlato(in.getId_plato()[i]));
        }
        menuPlatosDTO.setPlatos(p);
        return menuPlatosDTO;
    }
}
