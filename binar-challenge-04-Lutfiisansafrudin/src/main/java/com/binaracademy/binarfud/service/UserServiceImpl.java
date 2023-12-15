package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.User;
import com.binaracademy.binarfud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean addNewUser(User user) {
        return Optional.ofNullable(user)
                .map(newUser -> userRepository.save(user))
                .map(result-> {
                    boolean isSuccess = Objects.nonNull(result);
                    if(isSuccess) {
                        log.info("User {} successfully added", result.getUsername());
                    }
                    return isSuccess;
                })
                .orElseGet(() -> {
                    log.error("Failed to add new User");
                    return false;
                });
    }

    public Boolean updateUser(String username, User user) {
        return Optional.ofNullable(username)
                .map(userRepository::findByUsername)
                .map(user1 -> {
                    user1.builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .build();
                    log.info("User {} successfully updated", user1.getUsername());
                    userRepository.save(user1);
                    return true;
                })
                .orElseGet(() -> {
                    log.error("Failed to update User");
                    return false;
                });
    }

    public Boolean deleteUser(String username) {
        return Optional.ofNullable(username)
                .map(userRepository::findByUsername)
                   .map(user -> {
                        log.info("User {} successfully deleted", user.getUsername());
                        userRepository.delete(user);
                        return true;
                    })
                .orElseGet(() -> {
                    log.error("Failed to delete User");
                    return false;
                });
    }
}
