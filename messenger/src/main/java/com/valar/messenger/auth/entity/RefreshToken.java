package com.valar.messenger.auth.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class RefreshToken {

    @Id
    @Column
    private long user_id;

    @Column
    @NotBlank
    private String refresh_token;

}
