package com.jpa.WineOclocK.domain.entity;

import com.jpa.WineOclocK.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@WebMvcTest   // web API 를 테스트 할 때 사용

@RunWith(SpringRunner.class) //SpringRunner 라는 스프링 실행자를 사용. (스프링부트테스트 - JUnit 사이 연결자)
@SpringBootTest // H2 database 를 자동으로 실행
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After //단위 테스트가 끝날 때마다 수행
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void user_data_insert(){
        //given
        String email = "song@sookmyung.ac.kr";
        String password = "password1234";
        String username = "눈송";
        String birthday = "991002";

        userRepository.save(User.builder()
                .email(email)
                .password(password)
                .username(username)
                .birthday(birthday)
                .build());
        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
    }

}