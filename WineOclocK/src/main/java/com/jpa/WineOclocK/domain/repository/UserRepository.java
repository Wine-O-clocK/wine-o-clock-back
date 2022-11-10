package com.jpa.WineOclocK.domain.repository;

import com.jpa.WineOclocK.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
