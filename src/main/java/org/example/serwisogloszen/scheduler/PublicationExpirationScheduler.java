package org.example.serwisogloszen.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.serwisogloszen.model.EmailDetails;
import org.example.serwisogloszen.repository.PublicationRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class PublicationExpirationScheduler {

    private final PublicationRepository publicationRepository;
    private final JmsTemplate jmsTemplate;

    // runs every 2 minutes
    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void sendExpirationEmails() {
        var publications = publicationRepository
                .findByExpirationDateBeforeAndExpirationEmailSent(LocalDateTime.now(), false);

        publications.forEach(publication -> {
            var emailDetails = EmailDetails.builder()
                    .username(publication.getUser().getLogin())
                    .publicationTitle(publication.getTitle())
                    .type(EmailDetails.Type.PUBLICATION_EXPIRED)
                    .destinationMail(publication.getUser().getEmail())
                    .build();

            jmsTemplate.convertAndSend("mailbox", emailDetails);

            publication.setExpirationEmailSent(true);
            publicationRepository.save(publication);
        });
    }
}
