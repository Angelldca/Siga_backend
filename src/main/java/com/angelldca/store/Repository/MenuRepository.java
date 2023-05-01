package com.angelldca.store.Repository;

import com.angelldca.store.Enties.Menu;
import com.angelldca.store.Enties.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {


    @Transactional
    @Modifying
    @Query(value = "UPDATE  tb_menu SET estado='Inactivo' WHERE id_menu=:id ", nativeQuery = true)
    void markAsInactive(@Param("id") Long id);
}
