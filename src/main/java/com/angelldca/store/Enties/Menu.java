package com.angelldca.store.Enties;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tb_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_menu;

    @NotEmpty(message = "El evento del menu es obligatorio")
    private String evento;
    private double precio;
    private Date fecha;
    private String estado;

    @NotNull(message = "El menu debe contener al menos un plato")
    @NotEmpty(message = "El menu debe contener al menos un plato")
    private Long[] id_plato;



}
