package org.example.serwisogloszen.jms;

import org.example.serwisogloszen.model.EmailDetails;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailDetailsReceiver {

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveEmailDetails(EmailDetails emailDetails) {
        System.out.println(emailDetails);
    }
}
