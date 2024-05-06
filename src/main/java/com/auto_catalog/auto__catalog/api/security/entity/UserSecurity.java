package com.auto_catalog.auto__catalog.api.security.entity;

import com.auto_catalog.auto__catalog.store.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "user_security")
public class UserSecurity {
    @Id
    @SequenceGenerator(name="secSeqGen",sequenceName="user_security_id_seq", allocationSize=1)
    @GeneratedValue(generator="secSeqGen")
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_blocked")
    @NotNull
    private Boolean isBlocked;

}
