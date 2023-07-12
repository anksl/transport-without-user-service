package com.transport.service.impl;

import com.transport.service.MessageService;
import com.transport.service.PaymentService;
import com.transport.service.TransportationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@Service
public class MessageServiceImpl implements MessageService {
    private final Queue paymentQueue;
    private final Queue transportationQueue;
    private final JmsTemplate jmsTemplate;
    private final PaymentService paymentService;
    private final TransportationService transportationService;

    public MessageServiceImpl(@Qualifier("paymentQueue") Queue paymentQueue, @Qualifier("transportationQueue") Queue transportationQueue, JmsTemplate jmsTemplate, PaymentService paymentService, TransportationService transportationService) {
        this.paymentQueue = paymentQueue;
        this.transportationQueue = transportationQueue;
        this.jmsTemplate = jmsTemplate;
        this.paymentService = paymentService;
        this.transportationService = transportationService;
    }

    @Override
    public void findDebtors() {
        jmsTemplate.convertAndSend(paymentQueue, paymentService.findDebtors());
    }

    @Override
    public void createReport() {
        jmsTemplate.convertAndSend(transportationQueue, transportationService.createReport());
    }
}
