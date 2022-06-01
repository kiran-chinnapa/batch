package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
@EnableBatchProcessing
@Log4j2
@RequiredArgsConstructor
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	ItemReader<String> itemReader(){
		FlatFileItemReader<String> fileItemReader = new FlatFileItemReader<>();
		fileItemReader.setResource(new FileSystemResource("src/main/resources/input.txt"));
		fileItemReader.setLineMapper(new PassThroughLineMapper());
		return fileItemReader;
	}

	@Bean
	ItemProcessor<String,String> itemProcessor(){
		ItemProcessor<String,String> processor = new ItemProcessor<String, String>() {
			@Override
			public String process(String item) throws Exception {
				return item+System.currentTimeMillis();
			}
		};
		return processor;
	}

	@Bean
	ItemWriter<String> itemWriter(){
		FlatFileItemWriter<String> fileItemWriter = new FlatFileItemWriter<>();
		fileItemWriter.setResource(new FileSystemResource("src/main/resources/output.txt"));
		fileItemWriter.setLineAggregator(new PassThroughLineAggregator<>());
		fileItemWriter.setLineSeparator(System.lineSeparator());
		return fileItemWriter;
	}

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Bean
	Step step(){
		return stepBuilderFactory.get("my-step")
				.<String,String>chunk(1)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.build();
	}

	@Bean
	Job job(){
		return jobBuilderFactory.get("my-own-job")
				.incrementer(new RunIdIncrementer())
				.start(step())
				.build();
	}

}
