package com.ia.inv_arg.repository;

import com.ia.inv_arg.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRol(String rol);
}
