package com.transport.service;

import com.transport.api.dto.PaymentDto;
import com.transport.api.dto.jms.DebtorsMessageDto;
import com.transport.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    List<PaymentDto> getPayments(Integer pageNo, Integer pageSize, String sortBy);

    List<PaymentDto> findByPriceGreaterThan(BigDecimal price, Integer pageNo, Integer pageSize, String sortBy);

    List<PaymentDto> getForCurrentUser(Integer pageNo, Integer pageSize, String sortBy);

    PaymentDto updatePayment(Long id, PaymentDto newPayment);

    void setPaymentStatus(Payment payment);

    DebtorsMessageDto findDebtors();
}
