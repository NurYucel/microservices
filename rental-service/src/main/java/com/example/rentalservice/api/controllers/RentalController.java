package com.example.rentalservice.api.controllers;

import com.example.rentalservice.business.abstracts.RentalService;
import com.example.rentalservice.business.dto.requests.CreateRentalRequest;
import com.example.rentalservice.business.dto.requests.UpdateRentalRequest;
import com.example.rentalservice.business.dto.response.CreateRentalResponse;
import com.example.rentalservice.business.dto.response.GetAllRentalsResponse;
import com.example.rentalservice.business.dto.response.GetRentalResponse;
import com.example.rentalservice.business.dto.response.UpdateRentalResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService service;

    @GetMapping
    public List<GetAllRentalsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetRentalResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRentalResponse add(@Valid @RequestBody CreateRentalRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateRentalResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateRentalRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
