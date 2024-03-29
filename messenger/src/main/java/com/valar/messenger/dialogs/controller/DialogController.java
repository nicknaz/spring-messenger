package com.valar.messenger.dialogs.controller;

import com.valar.messenger.auth.entity.UserDetailsImpl;
import com.valar.messenger.dialogs.dto.DialogResponse;
import com.valar.messenger.dialogs.dto.DialogWithMessagesResponse;
import com.valar.messenger.exception.AccessDeniedException;
import com.valar.messenger.dialogs.service.DialogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "messenger/dialogs")
public class DialogController {

    private DialogService dialogService;

    @GetMapping()
    public List<DialogResponse> getAllDialogsByUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        log.info("User with id = " + Long.toString(userDetails.getId()) + " requested a list of dialogues ");
        return dialogService.getAllDialogsByUserId(userDetails.getId());
    }

    @GetMapping("/{id}")
    public DialogWithMessagesResponse getAllDialogByDialogId(@PathVariable("id") Long dialogId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!dialogService.checkAccessByUserId(userDetails.getId(), dialogId)) {
            throw new AccessDeniedException("You do not have access to the selected dialog!");
        }

        log.info("User with id = " + Long.toString(userDetails.getId())
                + " requested a dialogue with id = " + Long.toString(dialogId));
        return dialogService.getDialogWithMessagesById(dialogId);
    }

}