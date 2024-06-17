package org.example.serwisogloszen.repository;

import org.example.serwisogloszen.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByModerationState(Publication.ModerationState moderationState);

    List<Publication> findByModerationStateAndExpirationDateAfter(Publication.ModerationState moderationState, LocalDateTime expirationDate);
}
