package com.auto_catalog.auto__catalog.store.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true/*, nullable = false*/)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


}
