package com.chatapp.chatapppractice.models.entities;

import com.chatapp.chatapppractice.models.constants.UserValidationsConstants;
import com.chatapp.chatapppractice.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.util.Objects;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = UserValidationsConstants.MIN_SIZE_REAL_NAMES_USER, max = UserValidationsConstants.MAX_SIZE_REAL_NAMES_USER)
    private String fullname;

    @NotNull
    @Size(min = UserValidationsConstants.MIN_SIZE_REAL_NAMES_USER, max = UserValidationsConstants.MAX_SIZE_REAL_NAMES_USER)
    private String firstname;

    @NotNull
    @Size(min = UserValidationsConstants.MIN_SIZE_REAL_NAMES_USER, max = UserValidationsConstants.MAX_SIZE_REAL_NAMES_USER)
    private String lastname;

    @NotNull
    @Size(min = UserValidationsConstants.MIN_SIZE_USERNAME, max = UserValidationsConstants.MAX_SIZE_USERNAME)
    private String username;

    private String password;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    private UserRole userRole;

}
