package com.gana.fashionWardrobe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import io.github.cdimascio.dotenv.Dotenv;

@EntityScan(basePackages = "com.gana.model")
@SpringBootApplication
public class FashionWardrobeApplication {
	Dotenv dotenv = Dotenv.load();
	String mysqlHost = dotenv.get("MYSQL_HOST");
	String dbUsername = dotenv.get("DB_USERNAME");
	String dbPassword = dotenv.get("DB_PASSWORD");
	public static void main(String[] args) {
		SpringApplication.run(FashionWardrobeApplication.class, args);
	}

}
