package com.auto_catalog.auto__catalog.store.repository;

import com.auto_catalog.auto__catalog.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
    @Query("SELECT u FROM User u JOIN UserSecurity us ON u.userId = us.user.userId WHERE us.userLogin = :login")
    Optional<User> findByUserLogin(@Param("login") String login);
}

