package com.bugcatcher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.bugcatcher.service.StorageProperties;
import com.bugcatcher.service.StorageService;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class BugCatcherApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BugCatcherApplication.class, args);		
	}	
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
