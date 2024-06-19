package org.example.serwisogloszen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.serwisogloszen.model.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String sender;

    private final static String acceptationEmailTemplate =
            "Hello, %s! \n\n Your publication titled %s was accepted by moderator";

    private final static String expirationEmailTemplate =
            "Hello, %s! \n\n Your publication titled %s has expired";

    public void sendAcceptationEmail(EmailDetails details) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(details.getDestinationMail());
            message.setText(acceptationEmailTemplate.formatted(details.getUsername(), details.getPublicationTitle()));
            message.setSubject("Publication accepted");
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Error occured when sending email: " + e.getMessage());
        }
    }

    public void sendExpirationEmail(EmailDetails details) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(details.getDestinationMail());
            message.setText(String.format(expirationEmailTemplate, details.getUsername(), details.getPublicationTitle()));
            message.setSubject("Publication expired");
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Error occured when sending email: " + e.getMessage());
        }
    }
}
