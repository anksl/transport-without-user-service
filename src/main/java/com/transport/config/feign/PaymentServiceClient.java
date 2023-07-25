package com.transport.config.feign;

import com.transport.api.dto.PaymentDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @PostMapping(value = "/api/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    void savePayment(PaymentDto paymentDto);

    @GetMapping(value = "/api/payments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    PaymentDto getPaymentById(@PathVariable(value = "id") Long id);
}
