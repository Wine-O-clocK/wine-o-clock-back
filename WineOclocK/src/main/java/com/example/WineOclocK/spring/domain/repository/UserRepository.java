package com.example.WineOclocK.spring.domain.repository;

import com.example.WineOclocK.spring.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 소셜 로그인으로 반환되는 값 중 이메일을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 파단하기 위한 메서드
    Optional<User> findByEmail(String email);
}
