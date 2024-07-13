package com.ia.inv_arg.repository;

import com.ia.inv_arg.DTO.salida.UserDtoSalida;
import com.ia.inv_arg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
