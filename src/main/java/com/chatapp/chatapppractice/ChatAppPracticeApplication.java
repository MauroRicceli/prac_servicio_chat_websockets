package com.chatapp.chatapppractice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("CheckStyle")
@SpringBootApplication
public class ChatAppPracticeApplication {

	public static void main(String[] args) {

        Path envPath = Paths.get(".env");
        if (Files.exists(envPath)) {
            Dotenv dotenv = Dotenv.configure().filename(".env").load();
            dotenv.entries()
                    .forEach(entry -> System.setProperty(
                            entry.getKey(), entry.getValue()));
        }

		SpringApplication.run(ChatAppPracticeApplication.class, new String[0]);

	}

}
