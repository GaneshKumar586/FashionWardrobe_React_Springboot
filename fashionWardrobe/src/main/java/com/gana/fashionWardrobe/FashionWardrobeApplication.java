package com.gana.fashionWardrobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.gana.model")
@SpringBootApplication
public class FashionWardrobeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionWardrobeApplication.class, args);
	}

}
