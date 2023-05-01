package com.angelldca.store.Repository;

import com.angelldca.store.Enties.Factura;
import com.angelldca.store.Enties.Reserva;
import com.angelldca.store.Enties.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura,Long> {

    Factura findByReserva(Reserva reserva);
    List<Factura> findByUsuario(Usuario usuario);

}
