package net.bigmir.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class SimpleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Zip> zips = new LinkedList<>();

    public SimpleUser(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

}
