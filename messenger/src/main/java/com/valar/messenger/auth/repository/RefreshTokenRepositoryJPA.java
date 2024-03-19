package com.valar.messenger.auth.repository;

import com.valar.messenger.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepositoryJPA extends JpaRepository<RefreshToken, Long> {
}
