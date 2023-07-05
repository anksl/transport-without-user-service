package com.transport.service.impl;

import com.transport.api.dto.UserDto;
import com.transport.api.exception.EmailSendingException;
import com.transport.feign.UsersServiceClient;
import com.transport.model.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final UsersServiceClient usersServiceClient;

    public void sendSimpleMail(Email details) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        UserDto user = usersServiceClient.getCurrentUser();
        mailMessage.setFrom(user.getEmail());
        mailMessage.setTo(details.getRecipients());
        mailMessage.setText(details.getMsgBody());
        mailMessage.setSubject(details.getSubject());
        javaMailSender.send(mailMessage);
    }

    public void sendMailWithAttachment(Email details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        UserDto user = usersServiceClient.getCurrentUser();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(user.getEmail());
            mimeMessageHelper.setTo(details.getRecipients());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error while sending mail!");
            throw new EmailSendingException("Error while sending mail!");
        }
    }
}
