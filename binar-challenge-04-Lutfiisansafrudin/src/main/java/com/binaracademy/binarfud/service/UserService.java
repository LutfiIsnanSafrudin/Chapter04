package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Boolean addNewUser(User user);
    Boolean updateUser(String username, User user);
    Boolean deleteUser(String username);
}
