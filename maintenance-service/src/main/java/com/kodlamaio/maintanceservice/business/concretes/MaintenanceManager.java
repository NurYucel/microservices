package com.kodlamaio.maintanceservice.business.concretes;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.kodlamaio.commonpackage.events.maintenance.MaintenanceReturnedEvent;
import com.kodlamaio.commonpackage.kafka.producer.KafkaProducer;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.maintanceservice.business.abstracts.MaintenanceService;
import com.kodlamaio.maintanceservice.business.dto.requests.create.CreateMaintenanceRequest;
import com.kodlamaio.maintanceservice.business.dto.requests.update.UpdateMaintenanceRequest;
import com.kodlamaio.maintanceservice.business.dto.responses.create.CreateMaintenanceResponse;
import com.kodlamaio.maintanceservice.business.dto.responses.get.GetAllMaintenancesResponse;
import com.kodlamaio.maintanceservice.business.dto.responses.get.GetMaintenanceResponse;
import com.kodlamaio.maintanceservice.business.dto.responses.update.UpdateMaintenanceResponse;
import com.kodlamaio.maintanceservice.business.rules.MaintenanceBusinessRules;
import com.kodlamaio.maintanceservice.entities.Maintenance;
import com.kodlamaio.maintanceservice.repository.MaintenanceRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MaintenanceManager implements MaintenanceService {
    private final MaintenanceRespository repository;
    private final ModelMapperService mapper;
    private final MaintenanceBusinessRules rules;
    private final KafkaProducer producer;

    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        var maintenances = repository.findAll();
        var response = maintenances
                .stream()
                .map(maintenance -> mapper.forResponse().map(maintenance, GetAllMaintenancesResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        rules.checkIfMaintenanceExists(id);
        var maintenance = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);

        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(UUID carId) {
        rules.checkIfCarIsNotUnderMaintenance(carId);
        Maintenance maintenance = repository.findMaintenanceByCarIdAndIsCompletedFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());
        repository.save(maintenance);
        sendKafkaMaintenanceReturnedEvent(carId);
        GetMaintenanceResponse response = mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);

        return response;
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.checkCarAvailabilityForMaintenance(request.getCarId());
        rules.checkIfCarUnderMaintenance(request.getCarId());
        var maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(null);
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);
        //carService.changeState(request.getCarId(), State.MAINTENANCE);
        repository.save(maintenance);
        sendKafkaMaintenanceCreatedEvent(request.getCarId());

        var response = mapper.forResponse().map(maintenance, CreateMaintenanceResponse.class);

        return response;
    }

    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExists(id);
        Maintenance maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(id);
        repository.save(maintenance);
        var response = mapper.forResponse().map(maintenance, UpdateMaintenanceResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfMaintenanceExists(id);
        sendKafkaMaintenanceDeletedEvent(id);
        repository.deleteById(id);
    }

    private void sendKafkaMaintenanceDeletedEvent(UUID id) {
        var carId = repository.findById(id).orElseThrow().getCarId();
        producer.sendMessage(new MaintenanceDeletedEvent(carId), "maintenance-deleted");
    }

    private void sendKafkaMaintenanceReturnedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceReturnedEvent(carId), "maintenance-returned");
    }

    private void sendKafkaMaintenanceCreatedEvent(UUID carId) {
        producer.sendMessage(new MaintenanceCreatedEvent(carId), "maintenance-created");
    }
}
