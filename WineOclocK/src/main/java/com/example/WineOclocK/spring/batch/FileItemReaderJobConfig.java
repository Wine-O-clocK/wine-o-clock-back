package com.example.WineOclocK.spring.batch;

import com.example.WineOclocK.spring.domain.entity.Wine;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FileItemReaderJobConfig {
    private final JobBuilderFactory jobBuilderFactory; //Job : 하나의 배치 작업 단위
    private final StepBuilderFactory stepBuilderFactory; //Job 안에는 여러 Step 존재. Step 안에 Reader, Writer 등이 포함
    private final CsvReader csvReader; //csv 파일을 읽어오는 행위
    private final CsvWriter csvWriter; // 읽어온 데이터를 DB에 저장하는 행위

    private static final int chunkSize = 100; //chunk size만큼 트랜잭션을 실행한다는 의미

    @Bean
    public Job csvFileItemReaderJob() {
        return jobBuilderFactory.get("csvFileItemReaderJob")
                .start(csvFileItemReaderStep())
                .build();
    }

    @Bean
    public Step csvFileItemReaderStep() {
        return stepBuilderFactory.get("csvFileItemReaderStep")
                .<Wine, Wine>chunk(chunkSize)
                .reader(csvReader.csvFileItemReader())
                .writer(csvWriter)
                .build();
    }
}
