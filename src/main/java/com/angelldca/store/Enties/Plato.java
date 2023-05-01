package com.angelldca.store.Enties;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_plato")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plato;
    @NotEmpty(message = "El nombre del plato no debe estar vacío")
    private String nombre_plato;
    @NotNull(message = "El grmaje del plato es obligatorio")
    @NotEmpty(message = "El grmaje del plato es obligatorio")
    private String gramaje;

    private double precio_plato;

}
