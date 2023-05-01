package com.angelldca.store.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatoReservaDTO {
    private Long id_plato;
    private Date fechaInicio;
    private Date fechaFin;
}
