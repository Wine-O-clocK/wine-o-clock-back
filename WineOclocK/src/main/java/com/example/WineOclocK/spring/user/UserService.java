package com.example.WineOclocK.spring.user;

import com.example.WineOclocK.spring.domain.entity.*;
import com.example.WineOclocK.spring.user.dto.JoinDto;
import com.example.WineOclocK.spring.user.dto.LoginDto;
import com.example.WineOclocK.spring.user.UserRepository;
import com.example.WineOclocK.spring.wine.repository.NoteRepository;
import com.example.WineOclocK.spring.wine.repository.RatingRepository;
import com.example.WineOclocK.spring.wine.repository.SaveRepository;
import com.example.WineOclocK.spring.wine.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final WineRepository wineRepository;
    private final SaveRepository saveRepository;
    private final NoteRepository noteRepository;
    private final RatingRepository ratingRepository;

    public User getUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found user with id =" + id));
    }

    public List<Wine> getWineBySave(Long id){
        List<Save> saveList = saveRepository.findAllByUserId(id);
        List<Wine> saveWineList = new ArrayList<>();

        for(int i=0; i<saveList.size(); i++) {
            Wine wine = wineRepository.findById(saveList.get(i).getWineId())
                    .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 와인입니다"));
            saveWineList.add(wine);
        }
        return saveWineList;
    }

    public List<Wine> getWineByNote(Long id){
        List<Note> noteList = noteRepository.findAllByUserId(id);
        List<Wine> noteWineList = new ArrayList<>();

        for(int i=0; i<noteList.size(); i++) {
            Wine wine = wineRepository.findById(noteList.get(i).getWineId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 와인입니다"));
            noteWineList.add(wine);
        }
        return noteWineList;
    }
    public String userLikeTypeStrToList(List<String> userLikeTypeList) {
        //유저선호와인 타입 list -> str 로 변경작업
        StringBuilder sb = new StringBuilder();
        for (String wineType : userLikeTypeList) {
            sb.append(wineType).append(" ");
        }
        String userLikeTypeStr = sb.toString().substring(0, sb.length() - 1);
        return userLikeTypeStr;
    }

    /**
     * 로그인
     */
    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다."));

        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        // 로그인에 성공하면 토큰 생성 후 반환
        return null;
    }

    /**
     * 회원가입
     */
    @Transactional
    public void join(JoinDto joinDto) {
        //이메일 중복 확인
        if(userRepository.existsByEmail(joinDto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }
        // 와인당도 : 0 (단 와인 선호), 1(단 와인 불호), 2(상관없음)
        int userLikeSweetInt;
        if (joinDto.getUserLikeSweet() == 0){ userLikeSweetInt = 5; }
        else if (joinDto.getUserLikeSweet() == 1){ userLikeSweetInt = 1; }
        else { userLikeSweetInt = 3; }

        // 와인바디 : 0 (가벼운 와인 선호), 1 (무거운 와인 선호), 2(상관없음)
        int userLikeBodyInt;
        if (joinDto.getUserLikeBody() == 0){ userLikeBodyInt = 1;}
        else if (joinDto.getUserLikeBody() == 1) { userLikeBodyInt = 5; }
        else { userLikeBodyInt = 3; }

        try {
            User user = User.builder()
                    .email(joinDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(joinDto.getPassword())) //비밀번호 인코딩
                    .birthday(joinDto.getBirthday())
                    .username(joinDto.getUsername())

                    .userLikeType(userLikeTypeStrToList(joinDto.getUserLikeType()))
                    .userLikeSweet(userLikeSweetInt)
                    .userLikeBody(userLikeBodyInt)

                    .userLikeAroma1(joinDto.getUserLikeAroma1())
                    .userLikeAroma2(joinDto.getUserLikeAroma2())
                    .userLikeAroma3(joinDto.getUserLikeAroma3())

                    .role(Role.ROLE_USER_0) // 신규가입하자마자 등록되는 레벨
                    .build();
            userRepository.save(user);
        } catch (Exception exception) {
            throw new IllegalArgumentException("회원가입 서비스 빌드 오류");
        }
    }

    /**
     * 회원정보 수정
     */
    @Transactional
    public User updateUser(Long userId, JoinDto joinDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.update(bCryptPasswordEncoder.encode(joinDto.getPassword()),
                joinDto.getUsername(), joinDto.getBirthday(),
                userLikeTypeStrToList(joinDto.getUserLikeType()),
                joinDto.getUserLikeSweet(), joinDto.getUserLikeBody(),
                joinDto.getUserLikeAroma1(), joinDto.getUserLikeAroma2(), joinDto.getUserLikeAroma3());

        return user;
    }

    /**
     * 회원탈퇴
     */
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * 회원 레벨 업데이트
     * Lv0 -> rating 30개 미만
     * Lv1 -> rating 30개 이상 50개 미만
     * Lv2 -> rating 50개 이상
     */
    @Transactional
    public void updateUserRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        List<Rating> ratings = ratingRepository.findAllyByUserId(userId);

        System.out.println("========" + user.getUsername() + "님의 rating 개수는 " + ratings.size() + "입니다");
        System.out.println("========" + user.getUsername() + "님의 기존 role 은 " + user.getRole() + "입니다");

        if (ratings.size() >= 50) {
            user.updateRole(Role.ROLE_USER_2);
        } else if (ratings.size() >= 30) {
            user.updateRole(Role.ROLE_USER_1);
        } else {
            user.updateRole(Role.ROLE_USER_0);
        }
        System.out.println("========" + user.getUsername() + "님의 수정 role 은 " + user.getRole() + "입니다");
    }
}
