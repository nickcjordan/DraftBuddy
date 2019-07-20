package com.falifa.draftbuddy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.falifa" })
public class DraftBuddyAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(DraftBuddyAPIApplication.class, args);
	}
}
