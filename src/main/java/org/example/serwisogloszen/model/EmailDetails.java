package org.example.serwisogloszen.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class EmailDetails {
    private String destinationMail;
    private String username;
    private String publicationTitle;

    public enum Type {
        PUBLICATION_ACCEPTED,
        PUBLICATION_EXPIRED,
    }

    private Type type;
}
