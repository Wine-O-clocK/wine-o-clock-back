package com.example.WineOclocK.domain.repository;

import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;

//@WebMvcTest   // web API 를 테스트 할 때 사용

@RunWith(SpringRunner.class) //SpringRunner 라는 스프링 실행자를 사용. (스프링부트테스트 - JUnit 사이 연결자)
@SpringBootTest // H2 database 를 자동으로 실행
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;

    private void clear(){
        em.flush();
        em.clear();
    }

    @AfterEach
    public void after(){
        em.clear();
    }

    @Test
    public void 회원저장_성공() throws Exception{
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

//    @Test
//    public void 오류_회원가입시_이름이_없음() throws Exception {
//        //given
//        User user = User.builder().username("username").password("1234567890").nickName("NickName1").role(Role.USER).age(22).build();
//
//        //when, then
//        assertThrows(Exception.class, () -> memberRepository.save(member));
//    }

}