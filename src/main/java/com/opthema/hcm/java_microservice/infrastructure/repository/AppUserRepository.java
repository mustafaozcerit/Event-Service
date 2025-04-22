package com.opthema.hcm.java_microservice.infrastructure.repository;

import com.opthema.hcm.java_microservice.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);
    boolean existsByUsername(String username);
}
