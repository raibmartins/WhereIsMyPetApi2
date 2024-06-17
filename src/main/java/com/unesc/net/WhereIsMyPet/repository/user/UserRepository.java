package com.unesc.net.WhereIsMyPet.repository.user;

import com.unesc.net.WhereIsMyPet.entity.user.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUserByEmail(String email);

}
