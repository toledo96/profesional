package com.practice.project.repositories;

import com.practice.project.models.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaqueteRepository extends JpaRepository<Paquete,Long> {
}
