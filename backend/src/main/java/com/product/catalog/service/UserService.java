package com.product.catalog.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.product.catalog.model.User;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final String USER_CSV = "users.csv";

    public List<User> getAllUsers() {
        try (FileReader reader = new FileReader(USER_CSV)) {
            return new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read users CSV: " + e.getMessage());
        }
    }

    public Optional<User> validateUser(String username, String password) {
        return getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
    }
} 