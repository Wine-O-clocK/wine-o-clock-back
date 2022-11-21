package com.example.WineOclocK.spring.domain.repository;

import com.example.WineOclocK.spring.domain.entity.SocialUser;
import com.example.WineOclocK.spring.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String email);
}
