package com.rmb.limitOrderBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication

public class LimitOrderBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimitOrderBookApplication.class, args);
	}

}
