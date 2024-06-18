package org.example.serwisogloszen.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.serwisogloszen.Enum.Rola;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 5, max = 100)
    private String login;
    @Size(min = 5, max = 100)
    private String password;
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Rola rola;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Publication> publications;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "login = " + login + ", " +
                "rola = " + rola + ")";
    }
}
