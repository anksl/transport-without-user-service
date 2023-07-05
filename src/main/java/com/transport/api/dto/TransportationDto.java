package com.transport.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportationDto {
    private Long id;
    @Positive
    private short distance;
    private CargoDto cargo;
    private DeliveryDto loading;
    private DeliveryDto landing;
    private SecureUserDto user;
    private PaymentDto payment;
}
