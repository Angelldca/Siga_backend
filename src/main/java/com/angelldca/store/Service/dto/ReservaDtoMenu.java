package com.angelldca.store.Service.dto;


import com.angelldca.store.Enties.Usuario;
import lombok.Data;

@Data
public class ReservaDtoMenu {
    private Long id_reserva;
    private MenuPlatosDTO menu;
    private Long[] id_platosMenu;
    //private List<Plato> platos;
    private Usuario usuarioReserva;
}
