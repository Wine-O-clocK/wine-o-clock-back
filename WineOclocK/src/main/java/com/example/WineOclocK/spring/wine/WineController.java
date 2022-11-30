//package com.example.WineOclocK.spring.wine;
//
//import com.example.WineOclocK.spring.domain.entity.Wine;
//import lombok.RequiredArgsConstructor;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequiredArgsConstructor //private final 붙은 변수에 자동으로 생성자 만들어줌
//public class WineController {
//
//    private final WineRepository wineRepository;
//
//    @GetMapping("/wines")
//    public List<Wine> findAllMember() {
//        return wineRepository.findAll();
//    }
//
////    //테이블 리스트 가져오기
////    @GetMapping("/wines")
////    public List<Wine> list(){
////        return wineRepository.findAll();
////    }
////
////    @GetMapping("/")
////    public String wines(Model model) {
////        List<Wine> wines = wineRepository.findAll();
////        model.addAttribute("wines", wines);
////        return "basic/wines";
////    }
//
//    @GetMapping("/wines/{wineId}")
//    public String wine(@PathVariable long wineId, Model model) {
//        Optional<Wine> wine = wineRepository.findById(wineId);
//        model.addAttribute("wine", wine);
//        return "basic/wine.html";
//    }
//
//}
