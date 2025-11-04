package com.pvkr.jwt.config;
import com.pvkr.jwt.dao.UserRepository;
import com.pvkr.jwt.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Insert sample users if table is empty
            if (userRepository.count() == 0) {
                userRepository.save(new User("alice", passwordEncoder.encode("password1"), "ROLE_USER"));
                userRepository.save(new User("bob", passwordEncoder.encode("password2"), "ROLE_ADMIN"));
                userRepository.save(new User("charlie", passwordEncoder.encode( "password3"), "ROLE_USER"));
            }
        };
    }
}

