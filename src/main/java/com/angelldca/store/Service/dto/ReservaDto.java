package com.angelldca.store.Service.dto;


import com.angelldca.store.Enties.Menu;
import com.angelldca.store.Enties.Usuario;
import lombok.Data;

@Data
public class ReservaDto {
    private Long id_reserva;
    private Menu menu;
    private Long[] id_platosMenu;
    private Usuario usuarioReserva;
}
