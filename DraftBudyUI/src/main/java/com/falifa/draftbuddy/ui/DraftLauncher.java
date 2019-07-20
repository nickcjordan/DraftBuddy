package com.falifa.draftbuddy.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DraftLauncher extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DraftLauncher.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DraftLauncher.class, args);
	}

}
