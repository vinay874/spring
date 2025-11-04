package com.vinay.basicauth.config;

import com.vinay.basicauth.dao.UserRepository;
import com.vinay.basicauth.entity.Users;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("vinay").isEmpty()) {
                Users user = new Users("vinay", passwordEncoder.encode("vinay@123"), "ROLE_USER");
                userRepository.save(user);
            }

            if (userRepository.findByUsername("admin").isEmpty()) {
                Users admin = new Users("admin", passwordEncoder.encode("admin@123"), "ROLE_ADMIN");
                userRepository.save(admin);
            }
        };
    }
}

