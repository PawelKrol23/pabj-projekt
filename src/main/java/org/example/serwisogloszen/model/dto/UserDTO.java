package org.example.serwisogloszen.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class UserDTO
{
    @Size(min = 5, max = 100)
    private String login;
    @Size(min = 5, max = 100)
    private String password;
    @Email
    private String email;
}
