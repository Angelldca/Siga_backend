package com.angelldca.store.Service.dto;

import com.angelldca.store.Enties.Plato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuPlatosDTO {
    private Long id_menu;
    private String evento;
    private double precio;
    private Date fecha;
    private List<Plato> platos;
}
