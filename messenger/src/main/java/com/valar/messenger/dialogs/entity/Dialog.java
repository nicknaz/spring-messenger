package com.valar.messenger.dialogs.entity;

import com.valar.messenger.users.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dialogs")
public class Dialog {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "idFirstUser", referencedColumnName = "id")
    private User firstUser;

    @ManyToOne
    @JoinColumn(name = "idSecondUser", referencedColumnName = "id")
    private User secondUser;

}
