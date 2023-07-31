package com.transport.controller;

import com.transport.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Scheduled(cron = "${com.transport.cron.report}")
    @GetMapping("/createReport")
    public void createReport() {
        messageService.createReport();
    }
}
