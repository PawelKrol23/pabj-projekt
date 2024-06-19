package org.example.serwisogloszen.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class EmailDetails {
    private String to;
    private String subject;
    private String body;
}
