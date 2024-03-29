package com.example.WineOclocK.spring.user;

import com.example.WineOclocK.spring.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email); //중복가입방지
}
