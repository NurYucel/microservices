package com.kodlamaio.maintanceservice.repository;

import com.kodlamaio.maintanceservice.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaintenanceRespository extends JpaRepository<Maintenance, UUID> {
    Maintenance findMaintenanceByCarIdAndIsCompletedFalse(UUID carId);

    boolean existsByCarIdAndIsCompletedIsFalse(UUID carId);
}
