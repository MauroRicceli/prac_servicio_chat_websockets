package com.chatapp.chatapppractice.models.entities;

import com.chatapp.chatapppractice.models.constants.UserValidationsConstants;
import com.chatapp.chatapppractice.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Size(min = UserValidationsConstants.MIN_SIZE_REAL_NAMES_USER, max = UserValidationsConstants.MAX_SIZE_REAL_NAMES_USER)
    private String fullname;

    @NotBlank
    @Size(min = UserValidationsConstants.MIN_SIZE_REAL_NAMES_USER, max = UserValidationsConstants.MAX_SIZE_REAL_NAMES_USER)
    private String firstname;

    @NotBlank
    @Size(min = UserValidationsConstants.MIN_SIZE_REAL_NAMES_USER, max = UserValidationsConstants.MAX_SIZE_REAL_NAMES_USER)
    private String lastname;

    @NotBlank
    @Size(min = UserValidationsConstants.MIN_SIZE_USERNAME, max = UserValidationsConstants.MAX_SIZE_USERNAME)
    @Column(unique = true)
    private String username;

    //Para obligar en un futuro a cambiar el nombre de usuario desde el frontend al crear un usuario por oauth2.
    private boolean authModifiedUsername = false;

    private String password;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    private boolean verifiedEmail = false;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    @NotNull
    private UserRole userRole;

    private boolean auth2User;

}
