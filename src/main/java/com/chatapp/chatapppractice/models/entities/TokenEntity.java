package com.chatapp.chatapppractice.models.entities;

import com.chatapp.chatapppractice.constants.TokenConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public enum TokenType {
        BEARER
    }

    @NotNull
    @Column(unique = true, length = TokenConstants.MAX_LENGTH_TOKEN)
    private String token;

    @NotNull
    private boolean revoked;
    private boolean expirated;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    @JoinColumn(name = "userTokenOwner", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userOwner;
}
