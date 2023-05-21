package com.kodlamaio.commonpackage.utils.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {//Base/Super class
    @NotBlank(message = "Kart numarası boş bırakılamaz")
    @Length(min = 16, max = 16, message = "Kart numarası 16 haneli olmalı")

    private String cardNumber;
    @NotBlank
    @Length(min = 5)
    private String cardHolder;
    @Min(value = 2023)
    private int cardExpirationYear;
    @Min(value = 1)
    @Max(value = 12)
    private int cardExpirationMonth;
    @NotBlank
    @Length(min = 3, max = 3)
    private String cardCvv;
}