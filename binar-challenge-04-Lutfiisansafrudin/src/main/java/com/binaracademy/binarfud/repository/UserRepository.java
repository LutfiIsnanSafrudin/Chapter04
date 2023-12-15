package com.binaracademy.binarfud.repository;

import com.binaracademy.binarfud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
//    User deleteByUsername(String username);
}
