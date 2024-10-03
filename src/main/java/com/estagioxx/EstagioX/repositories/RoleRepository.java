package com.estagioxx.EstagioX.repositories;

import com.estagioxx.EstagioX.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
