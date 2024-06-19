package org.example.serwisogloszen.jms;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.EmailDetails;
import org.example.serwisogloszen.service.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailDetailsReceiver {
    private final EmailService emailService;

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveEmailDetails(EmailDetails emailDetails) {
        if (emailDetails.getType() == EmailDetails.Type.PUBLICATION_ACCEPTED) {
            emailService.sendAcceptationEmail(emailDetails);
        }
    }
}
