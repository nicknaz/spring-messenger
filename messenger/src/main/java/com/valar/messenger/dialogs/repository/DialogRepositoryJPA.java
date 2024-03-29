package com.valar.messenger.dialogs.repository;

import com.valar.messenger.dialogs.entity.Dialog;
import com.valar.messenger.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepositoryJPA extends JpaRepository<Dialog, Long> {


    Optional<Dialog> findById(Long id);

    @Query(value = "select dg from Dialog as dg " +
            "where (dg.firstUser.id = :firstUserId and dg.secondUser.id = :secondUserId) " +
            "or (dg.secondUser.id = :firstUserId and dg.firstUser.id = :secondUserId)")
    Dialog findDialogByUsers(Long firstUserId, Long secondUserId);

    @Query(value = "select dg from Dialog as dg " +
            "where (dg.firstUser.id = :userId) " +
            "or (dg.secondUser.id = :userId)")
    List<Dialog> findDialogsByUser(Long userId);

    @Query(value = "select dg from Dialog as dg " +
            "where (dg.id = :dialogId) " +
            "and ((dg.firstUser.id = :userId) " +
            "or (dg.secondUser.id = :userId))")
    List<Dialog> findDialogsByUserAndDialogId(Long userId, Long dialogId);

}