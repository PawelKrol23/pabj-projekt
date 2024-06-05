package org.example.serwisogloszen.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class PublicationDTO {
    @Size(min = 5, max = 100)
    private String title = "";
    @Size(min = 5, max = 1000)
    private String description = "";
    @NotNull
    private String categoryName = "";
}
