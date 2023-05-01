package com.angelldca.store.Repository;

import com.angelldca.store.Enties.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRepository extends JpaRepository<Usuario,Long> {
    Usuario findByUsername(String username);
}
