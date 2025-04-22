package com.opthema.hcm.java_microservice.infrastructure.repository;

import com.opthema.hcm.java_microservice.domain.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
}
