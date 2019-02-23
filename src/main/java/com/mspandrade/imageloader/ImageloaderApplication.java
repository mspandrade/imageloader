package com.mspandrade.imageloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ImageloaderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ImageloaderApplication.class, args);
	}

}
