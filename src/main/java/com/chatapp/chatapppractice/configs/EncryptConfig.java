package com.chatapp.chatapppractice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncryptConfig {

    /**
     * Encrypter used for the passwords.
     * @return encrypter.
     */
    @Bean
    public PasswordEncoder obtenerEncriptador() {
        return new BCryptPasswordEncoder();
    }
}
