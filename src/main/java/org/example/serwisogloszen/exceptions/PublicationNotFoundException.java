package org.example.serwisogloszen.exceptions;

public class PublicationNotFoundException extends RuntimeException {
    public PublicationNotFoundException(Long publicationId) {
        super("Publication with ID " + publicationId + " not found.");
    }
}