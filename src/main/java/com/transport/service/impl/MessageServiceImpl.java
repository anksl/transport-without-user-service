package com.transport.service.impl;

import com.transport.service.MessageService;
import com.transport.service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final Queue transportationQueue;
    private final JmsTemplate jmsTemplate;
    private final TransportationService transportationService;

    @Override
    public void createReport() {
        jmsTemplate.convertAndSend(transportationQueue, transportationService.createReport());
    }
}
