package com.valar.messenger.dialogs.entity;

import com.valar.messenger.users.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "idDialog", referencedColumnName = "id")
    private Dialog idDialog;

    @ManyToOne
    @JoinColumn(name = "idSender", referencedColumnName = "id")
    private User idSender;

    @Column
    private String text;

    @Column
    private LocalDateTime dateTime;

}
