package org.example.serwisogloszen.model.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class CategoryDTO {
    @Size(min = 5, max = 100)
    private String name = "";
}
