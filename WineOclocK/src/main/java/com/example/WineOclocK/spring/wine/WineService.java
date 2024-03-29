package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.user.entity.Role;
import com.example.WineOclocK.spring.user.entity.User;
import com.example.WineOclocK.spring.wine.dto.NoteReqDto;
import com.example.WineOclocK.spring.wine.dto.SearchReqDto;
import com.example.WineOclocK.spring.wine.dto.SearchWineDto;
import com.example.WineOclocK.spring.wine.entity.Note;
import com.example.WineOclocK.spring.wine.entity.Rating;
import com.example.WineOclocK.spring.wine.entity.Save;
import com.example.WineOclocK.spring.wine.entity.Wine;
import com.example.WineOclocK.spring.wine.repository.NoteRepository;
import com.example.WineOclocK.spring.wine.repository.RatingRepository;
import com.example.WineOclocK.spring.wine.repository.SaveRepository;
import com.example.WineOclocK.spring.wine.repository.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WineService {

    private final WineRepository wineRepository;
    private final RatingRepository ratingRepository;
    private final SaveRepository saveRepository;
    private final NoteRepository noteRepository;

    public Wine getWine (long wineId) {
        return wineRepository.findById(wineId)
                .orElseThrow(() -> new IllegalArgumentException("not found wine with id =" + wineId));
    }

    public Note getNote (long userId, long wineId) {
        return noteRepository.findByUserIdAndWineId(userId, wineId);
    }

    public Rating getRating (long userId, long wineId) {
        return ratingRepository.findByUserIdAndWineId(userId, wineId)
                .orElseThrow(() -> new IllegalArgumentException("not found rating"));
    }

    /**
     * 추천 알고리즘
     * json 형식으로 fastApi 에게 보냄
     */
    // 콘텐츠기반 추천 알고리즘 데이터 전처리
    @Transactional
    public Map<String, Object> makeRecommendRequest(User user) {

        Map<String, Object> map = new HashMap<>();

        //유저정보를 전처리화
        map.put("userLikeType", user.getUserLikeType());
        map.put("userLikeSweet", user.getUserLikeSweet());
        map.put("userLikeBody", user.getUserLikeBody());

        map.put("userLikeAroma1", user.getUserLikeAroma1());
        map.put("userLikeAroma2", user.getUserLikeAroma2());
        map.put("userLikeAroma3", user.getUserLikeAroma3());

        if (user.getRole() != Role.ROLE_USER_0) {
            map.put("ratings", ratingRepository.findAll());
            map.put("highRatingWine", searchHighRating(user.getUserId()));
            map.put("userId", user.getUserId());
        }
        return map;
    }

    @Transactional
    public String searchHighRating(Long userId) {
        List<Rating> ratingList = ratingRepository.findByUserIdOrderByRatingDesc(userId);
        // 0~3 사이 난수만들기
        Random random = new Random();
        int num = random.nextInt(3);
        return ratingList.get(num).getWineName();
    }

    public List<Wine> flaskResponseParsing(User user, String string) {
        string = string.substring(1, string.length()-2); // 앞 뒤 '[', ']' 제거
        string = string.replaceAll("\'", "");
        String[] strSplit = string.split(","); // , 를 기준으로 나눔

        List<Wine> wineResult = new ArrayList<>();

        if(user.getRole() == Role.ROLE_USER_0) {
            for(String s : strSplit) {
                Wine wine = wineRepository.findById(Long.parseLong(s))
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 와인입니다."));
                wineResult.add(wine);
            }
        } else {
            for(String s: strSplit) {
                Wine wine = wineRepository.findByWineName(s.trim())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 와인입니다."));
                wineResult.add(wine);
            }
        }
        return wineResult;
    }

    /**
     * 키워드 검색 기능
     */
    @Transactional
    public List<SearchWineDto> searchWines (Long userId, String keyword){
        List<Wine> wines = wineRepository.findByWineNameContaining(keyword);
        List<SearchWineDto> wineDtoList = new ArrayList<>();

        if(wines.isEmpty()) {
            return wineDtoList;
        }
        for(Wine wine : wines) {
            wineDtoList.add(this.convertEntityToDto(wine));
            if(userId != null) insertRating(userId, wine.getId(), 3); //이게 문제군
        }
        return wineDtoList;
    }

    /**
     * 필터링 검색 기능
     */
    @Transactional
    public List<SearchWineDto> searchByFiltering(SearchReqDto searchReqDto) {
        Specification<Wine> spec = (root, query, criteriaBuilder) -> null;

        // ex. 아로마 -> '사과', '베리', null
        // 사과결과 -> 아로마1='사과' or 아로마2='사과' or 아로마3='사과'
        // 베리결과 -> 아로마1='베리' or 아로마2='베리' or 아로마3='베리'
        // 아로마결과 -> 사과결과 or 베리결과

        if (searchReqDto.getAroma1() != null) {
            spec = spec.or(WineSpecification.equalAroma1(searchReqDto.getAroma1()));
            spec = spec.or(WineSpecification.equalAroma2(searchReqDto.getAroma1()));
            spec = spec.or(WineSpecification.equalAroma3(searchReqDto.getAroma1()));
        }
        if (searchReqDto.getAroma2() != null){
            spec = spec.or(WineSpecification.equalAroma1(searchReqDto.getAroma2()));
            spec = spec.or(WineSpecification.equalAroma2(searchReqDto.getAroma2()));
            spec = spec.or(WineSpecification.equalAroma3(searchReqDto.getAroma2()));
        }
        if (searchReqDto.getAroma3() != null){
            spec = spec.or(WineSpecification.equalAroma1(searchReqDto.getAroma3()));
            spec = spec.or(WineSpecification.equalAroma2(searchReqDto.getAroma3()));
            spec = spec.or(WineSpecification.equalAroma3(searchReqDto.getAroma3()));
        }
        if (searchReqDto.getType() != null) spec = spec.and(WineSpecification.equalWineType(searchReqDto.getType()));
        if (searchReqDto.getPrice() != 0) spec = spec.and(WineSpecification.equalWinePrice(searchReqDto.getPrice()));

        List<Wine> wines = wineRepository.findAll(spec);
        List<SearchWineDto> wineDtoList = new ArrayList<>();

        if(wines.isEmpty()) {
            return wineDtoList;
        }
        for(Wine wine : wines) {
            wineDtoList.add(this.convertEntityToDto(wine));
        }
        return wineDtoList;
    }

    /**
     * Wine Dto -> wine Entity 변경
     */
    public SearchWineDto convertEntityToDto(Wine wine){
        return SearchWineDto.builder()
                .wineId(wine.getId())
                .wineName(wine.getWineName())
                .wineNameEng(wine.getWineNameEng())
                .wineImage(wine.getWineImage())
                .wineType(wine.getWineType())
                .winePrice(wine.getWinePrice())
                .wineVariety(wine.getWineVariety())
                .wineSweet(wine.getWineSweet())
                .wineBody(wine.getWineBody())
                .aroma1(wine.getAroma1())
                .aroma2(wine.getAroma2())
                .aroma3(wine.getAroma3()).
                build();
    }

    /**
     * 와인 저장하기 기능
     * insert -> 저장하기
     * delete -> 저장취소
     * exist -> 존재여부
     */
    @Transactional
    public void insertSave (Long userId, Long wineId) {
        //중복저장 방지 (없는 경우에만 저장할 수 있도록)
        if(!existSave(userId, wineId)) {
            Save save = Save.builder()
                .userId(userId)
                .wineId(wineId)
                .build();
            saveRepository.save(save);
        }
    }

    @Transactional
    public void deleteSave (Long userId, Long wineId) {
        saveRepository.deleteByUserIdAndWineId(userId,wineId);
    }

    public boolean existSave (Long userId, Long wineId) {
        return saveRepository.existsByUserIdAndWineId(userId, wineId);
    }

    public boolean existNote (Long userId, Long wineId){
        return noteRepository.existsByUserIdAndWineId(userId, wineId);
    }

    /**
     * rating 반영하기
     */
    @Transactional
    public void insertRating (Long userId, Long wineId, int num) {

        if(userId == 0) return; //userId == 0 : 비회원이라는 의미

        //이미 데이터베이스에 있는지 확인
        if (ratingRepository.existsByUserIdAndWineId(userId, wineId)) {
            Rating rating = getRating(userId, wineId);
            //기존 값 vs num 값 비교 후 num 값이 높으면 새로 업데이트
            if (num > rating.getRating()) {
                rating.update(num);
            }

        } else { //디비에 존재 X -> 새로 디비에 저장
            Rating rating = Rating.builder()
                    .userId(userId)
                    .wineId(wineId)
                    .wineName(getWine(wineId).getWineName())
                    .rating(num)
                    .build();
            ratingRepository.save(rating);
        }
    }

    /**
     * 테이스팅 노트
     */
    @Transactional
    public Note insertNote(Long userId, Long wineId, NoteReqDto noteReqDto) {
        try {
            Note note = Note.builder()
                    .userId(userId)
                    .wineId(wineId)
                    .grade(noteReqDto.getGrade()).review(noteReqDto.getReview())
                    .build();
            return noteRepository.save(note);
        } catch (Exception exception) {
            throw new IllegalArgumentException("------note build error");
        }
    }

    @Transactional
    public Note updateNote(Long userId, Long wineId, NoteReqDto noteReqDto) {
        Note note = noteRepository.findByUserIdAndWineId(userId, wineId);
        note.update(noteReqDto.getGrade(), noteReqDto.getReview());
        return note;
    }

    @Transactional
    public Long deleteNote(Long userId, Long wineId) {
        Long noteId = noteRepository.findByUserIdAndWineId(userId, wineId).getNoteId();
        noteRepository.deleteById(noteId);
        return noteId;
    }
}
