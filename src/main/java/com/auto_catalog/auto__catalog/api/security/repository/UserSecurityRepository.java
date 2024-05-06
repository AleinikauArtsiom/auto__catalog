package com.auto_catalog.auto__catalog.api.security.repository;

import com.auto_catalog.auto__catalog.api.security.entity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {
    Optional<UserSecurity> findByUserLogin(String userLogin);
}
