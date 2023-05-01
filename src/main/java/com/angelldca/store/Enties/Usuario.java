package com.angelldca.store.Enties;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "tb_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    private String nombre;
    private String apellidos;
    private String ci;
    private String estado;
    private int edificio;
    private int apto;
    private String password;
    private String Solapin;
    private String categoria;
    @Email
    private String correo;
    private String username;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "id_rol")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handle"})
    private Rol rol;
    private byte[] imagen;

}
