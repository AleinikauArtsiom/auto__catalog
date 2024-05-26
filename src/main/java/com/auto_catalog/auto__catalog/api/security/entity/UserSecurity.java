package com.auto_catalog.auto__catalog.api.security.entity;

import com.auto_catalog.auto__catalog.store.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "user_security")
public class UserSecurity {
    @Id
    @GeneratedValue(generator="security_Gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="security_Gen",sequenceName="security_Gen", allocationSize=1)
    private Long id;

    @Column(name = "user_login")
    @NotNull
    private String userLogin;

    @Column(name = "user_password")
    @NotNull
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @NotNull
    private Roles role;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_blocked")
    @NotNull
    private Boolean isBlocked;

}
