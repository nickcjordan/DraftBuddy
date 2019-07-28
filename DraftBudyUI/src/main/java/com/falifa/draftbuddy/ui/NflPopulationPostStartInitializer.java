package com.falifa.draftbuddy.ui;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.manager.NFLTeamManager;

@Component
public class NflPopulationPostStartInitializer implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(NflPopulationPostStartInitializer.class);
	
	@Autowired
	private NFLTeamManager teamManager;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		teamManager.initializeNFL();
		log.info("Initialized {} players in the NFLTeamManager", Optional.ofNullable(teamManager.getAllAvailablePlayersByADP()).map(x -> x.size()).orElse(0));
	}

}
