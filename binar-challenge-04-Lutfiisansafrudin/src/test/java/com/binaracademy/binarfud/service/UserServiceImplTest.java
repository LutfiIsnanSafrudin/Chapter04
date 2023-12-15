package com.binaracademy.binarfud.service;


import com.binaracademy.binarfud.model.User;
import com.binaracademy.binarfud.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .username("SampleUser")
                .password("SamplePassword")
                .email("sample@mail.com")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    public void testAddNewUser() {
        User user = User.builder()
                .username("SampleUserCreate")
                .password("SamplePassword")
                .email("sample_create@mail.com")
                .build();
        boolean result = userService.addNewUser(user);
        assertTrue(result);
    }

    @Test
    public void testUpdateProduct() {
        User user = User.builder()
                .username("SampleUserUpdate")
                .password("SamplePasswordUpdate")
                .email("sample_update@mail.com")
                .build();
        boolean result = userService.updateUser("SampleUser", user);
        assertTrue(result);
    }

    @Test
    public void testDeleteUser() {
        boolean result = userService.deleteUser("SampleUser");
        assertTrue(result);
    }
}
