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
@Table(name = "tb_factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_factura;
    private String estado;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "id_reserva")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handle"})
    private Reserva reserva;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "id_usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handle"})
    private Usuario usuario;
}
