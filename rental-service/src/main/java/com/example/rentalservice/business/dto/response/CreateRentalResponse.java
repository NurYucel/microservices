package com.example.rentalservice.business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalResponse {
    private UUID id;
    private UUID carId;
    private double dailyPrice;
    private double totalPrice;
    private int rentedForDays;
    private LocalDate rentedAt;
}
