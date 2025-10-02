package com.chatapp.chatapppractice.models.entities;

import com.chatapp.chatapppractice.models.enums.TokenType;
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

    /**
     * Maximum length for the refresh token in the db.
     */
    public static final int MAX_LENGTH_TOKEN = 2000;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true, length = MAX_LENGTH_TOKEN)
    private String token;

    @NotNull
    private boolean revoked;
    private boolean expired;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    @JoinColumn(name = "userTokenOwner", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userOwner;
}
