package com.valar.messenger.users.repository;

import com.valar.messenger.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String nickname);

    Optional<User> findById(Long id);

    @Query(value = "select usr from User as usr " +
            "where (usr.username like lower(:username))")
    List<User> findByUserSearch(@Param("username") String text);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
