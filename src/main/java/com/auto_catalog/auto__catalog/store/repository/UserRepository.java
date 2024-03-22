package com.auto_catalog.auto__catalog.store.repository;


import com.auto_catalog.auto__catalog.store.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    <S extends User> List<S>findAll(Example<S> example);

    Optional<User> findById(Long id);

    boolean deleteUserById(Long id);

    boolean createUser(User user);

    boolean updateUser(User user);
}
