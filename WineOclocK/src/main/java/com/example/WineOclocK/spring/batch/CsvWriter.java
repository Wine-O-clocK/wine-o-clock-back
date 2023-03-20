package com.example.WineOclocK.spring.batch;

import com.example.WineOclocK.spring.wine.entity.Wine;
import com.example.WineOclocK.spring.wine.repository.WineRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<Wine> {

    private final WineRepository wineRepository;

    @Override
    public void write(List<? extends Wine> list) throws Exception {
        System.out.println("CsvWriter.write");
        wineRepository.saveAll(new ArrayList<Wine>(list));
//        List<Wine> tempWineList = new ArrayList<>();
//
//        list.forEach(getWine -> {
//            Wine tempWine = new Wine();
//            tempWine.setWineName(getWine.getWineName());
//            tempWine.setWineNameEng(getWine.getWineNameEng());
//            tempWine.setWineImage(getWine.getWineImage());
//            tempWine.setWineType(getWine.getWineName());
//            tempWine.setWineName(getWine.getWineName());
//            tempWine.setWineName(getWine.getWineName());;
//
//            tempWineList.add(tempWine);
//        });
//
//        wineRepository.saveAll(tempWineList);
    }
}
