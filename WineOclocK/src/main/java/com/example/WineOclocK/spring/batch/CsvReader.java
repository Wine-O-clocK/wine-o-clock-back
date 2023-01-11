package com.example.WineOclocK.spring.batch;

import com.example.WineOclocK.spring.domain.entity.Wine;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class CsvReader {
    @Bean
    public FlatFileItemReader<FoodDto> csvFileItemReader() {
        /* file read */
        FlatFileItemReader<Wine> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("/wine_list.csv"));
        //flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8"); // encoding

        /* read하는 데이터를 내부적으로 LineMapper을 통해 Mapping */
        DefaultLineMapper<Wine> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(","); //csv 파일에서 구분자 설정
        delimitedLineTokenizer.setNames("wineImage","wineName","wineNameEng","wineType","winePrice","wineSweet",
                "wineBody","wineVariety","aroma1","aroma2","aroma3"); // 각각의 데이터 이름 설정 - 엔티티 필드의 이름과 동일하게 설정하면 된다.
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
        BeanWrapperFieldSetMapper<Wine> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Wine.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}
