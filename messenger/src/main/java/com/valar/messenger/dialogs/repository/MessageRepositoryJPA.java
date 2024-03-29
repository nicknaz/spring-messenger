package com.valar.messenger.dialogs.repository;

import com.valar.messenger.dialogs.entity.Dialog;
import com.valar.messenger.dialogs.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepositoryJPA extends JpaRepository<Message, Long> {

    List<Message> findAllByIdDialogOrderByDateTimeDesc(Dialog idDialog, Pageable page);

}