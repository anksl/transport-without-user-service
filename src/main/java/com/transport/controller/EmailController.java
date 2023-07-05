package com.transport.controller;

import com.transport.model.Email;
import com.transport.service.PaymentService;
import com.transport.service.TransportationService;
import com.transport.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RequestMapping("/api/emails")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final PaymentService paymentService;
    private final TransportationService transportationService;

    @PostMapping("/send")
    public void sendMail(@RequestBody Email details) {
        emailService.sendSimpleMail(details);
    }

    @PostMapping("/sendWithAttachment")
    public void sendMailWithAttachment(@RequestBody Email details) {
        emailService.sendMailWithAttachment(details);
    }

    @Scheduled(cron = "0 0 0 10 * ?")
    @PostMapping("/sendCustomerReminder")
    public void sendReminder() {
        emailService.sendSimpleMail(paymentService.remindDebtors());
    }

    @Scheduled(cron = "0 0 0 20 * ?")
    @PostMapping("/sendReport")
    public void sendReport() {
        emailService.sendSimpleMail(transportationService.createReport());
    }
}

