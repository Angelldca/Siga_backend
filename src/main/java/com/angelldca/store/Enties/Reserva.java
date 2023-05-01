package com.angelldca.store.Enties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "tb_reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reserva;
    private String estado;
    private Date fecha_reserva;
    private double precio;
    //@NotNull(message = "La reserva debe contener al menos un menu")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "id_menu")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handle"})
    private Menu menu;

    private Long[] id_platosMenu;
    @NotNull(message = "La reserva debe contener un usuario")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "id_usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handle"})
    private Usuario usuarioReserva;


}
