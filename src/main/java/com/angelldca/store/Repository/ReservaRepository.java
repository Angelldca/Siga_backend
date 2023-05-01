package com.angelldca.store.Repository;

import com.angelldca.store.Enties.Reserva;
import com.angelldca.store.Enties.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {

    @Modifying
    @Query(value = "Select * from tb_reserva\n" +
                     "\tWHERE fecha_reserva between :startDate AND :endDate", nativeQuery = true)
    public List<Reserva> findByFecha_reserva(@Param("startDate") Date startDate, @Param("endDate")Date endDate);


    List<Reserva> findByUsuarioReserva(Usuario user);

}
