package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Wine;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor //private final 붙은 변수에 자동으로 생성자 만들어줌
public class WineController {


//    //테이블 리스트 가져오기
//    @GetMapping("/wines")
//    public List<Wine> list(){
//        return wineRepository.findAll();
//    }
//
//    @GetMapping("/")
//    public String wines(Model model) {
//        List<Wine> wines = wineRepository.findAll();
//        model.addAttribute("wines", wines);
//        return "basic/wines";
//    }

}
