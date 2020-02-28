package com.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootWalletTddApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWalletTddApplication.class, args);
	}

}
