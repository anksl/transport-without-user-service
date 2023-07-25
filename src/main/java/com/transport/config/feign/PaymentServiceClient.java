package com.transport.config.feign;

import com.transport.api.dto.PaymentDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @PostMapping("/api/payments")
    @Headers("Content-Type: application/json")
    void savePayment(PaymentDto paymentDto);

    @GetMapping("/api/payments/{id}")
    @Headers("Content-Type: application/json")
    PaymentDto getPaymentById(@PathVariable(value = "id") Long id);
}
