package com.kodlamaio.maintanceservice.business.abstracts;

import com.kodlamaio.maintanceservice.business.dto.requests.create.CreateMaintenanceRequest;
import com.kodlamaio.maintanceservice.business.dto.requests.update.UpdateMaintenanceRequest;
import com.kodlamaio.maintanceservice.business.dto.responses.create.CreateMaintenanceResponse;
import com.kodlamaio.maintanceservice.business.dto.responses.get.GetAllMaintenancesResponse;
import com.kodlamaio.maintanceservice.business.dto.responses.get.GetMaintenanceResponse;
import com.kodlamaio.maintanceservice.business.dto.responses.update.UpdateMaintenanceResponse;

import java.util.List;
import java.util.UUID;

public interface MaintenanceService {
    List<GetAllMaintenancesResponse> getAll();

    GetMaintenanceResponse getById(UUID id);

    GetMaintenanceResponse returnCarFromMaintenance(UUID carId);

    CreateMaintenanceResponse add(CreateMaintenanceRequest request);

    UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request);

    void delete(UUID id);
}
