package org.example.serwisogloszen.model.dto;

import lombok.Data;

@Data
public class PublicationDTO {
    private String title = "";
    private String description = "";
    private String author = "";
    private String publisher = "";
    private String categoryName = "";
}
