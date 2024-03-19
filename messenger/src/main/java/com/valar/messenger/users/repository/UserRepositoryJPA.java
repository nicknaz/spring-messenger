package com.valar.messenger.users.repository;

import com.valar.messenger.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String nickname);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
