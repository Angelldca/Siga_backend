package com.angelldca.store.Repository;

import com.angelldca.store.Enties.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlatoRepository extends JpaRepository <Plato,Long> {


   @Query(value = "SELECT * FROM tb_plato WHERE nombre_plato=:nombre_plato limit 1", nativeQuery = true)
    Plato selectPlatoByName(@Param("nombre_plato") String nombre_plato);
}
