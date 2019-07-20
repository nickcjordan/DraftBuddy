package com.falifa.draftbuddy.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class PlayerDataStorageInitializer implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerDataStorageInitializer.class);
	
	@Autowired
	private PlayerDataStorage storage;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		storage.build(true);
		log.info("Initialized {} players from the cache", Optional.ofNullable(storage.getAllPlayers()).map(x -> x.size()).orElse(0));
	}

}
