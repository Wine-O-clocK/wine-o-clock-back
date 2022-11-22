package com.example.WineOclocK.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

//    @Transactional
//    public void join(JoinDto joinDto) throws BaseException {
//        // 아이디 중복 확인
//        if(checkUsername(joinDto.getUsername())){
//            throw new BaseException(USERS_EXISTS_USERNAME);
//        }
//
//        // 이메일 중복 확인
//        if(checkEmail(joinDto.getEmail())){
//            throw new BaseException(USERS_EXISTS_EMAIL);
//        }
//
//        String encodePassword;
//        try{
//            //암호화
//            encodePassword = bCryptPasswordEncoder.encode(joinDto.getPassword());
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
//        }
//
//        try{
//            User user = User.builder()
//                    .name(joinDto.getName())
//                    .username(joinDto.getUsername())
//                    .password(encodePassword)
//                    .phoneNumber(joinDto.getPhoneNumber())
//                    .profileImgUrl(joinDto.getProfileImgUrl())
//                    .email(joinDto.getEmail())
//                    .address(joinDto.getAddress())
//                    .createDate(joinDto.getCreateDate())
//                    .organizationName(joinDto.getOrganizationName())
//                    .role(joinDto.getRole()).build();
//
//            userRepository.save(user);
//
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//      }
}
