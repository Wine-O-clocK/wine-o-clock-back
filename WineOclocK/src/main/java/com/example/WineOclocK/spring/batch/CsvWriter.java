package com.example.WineOclocK.spring.batch;

import com.example.WineOclocK.spring.domain.entity.Wine;
import com.example.WineOclocK.spring.wine.WineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.batch.api.chunk.ItemWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<Wine> {

    private final WineRepository wineRepository;

    @Override
    public void write(List<? extends Wine> list) throws Exception {
        wineRepository.saveAll(new ArrayList<Wine>(list));
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void writeItems(List<Object> items) throws Exception {

    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
