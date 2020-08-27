package com.falifa.draftbuddy.ui.utils;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;

@Component
public class NflPopulationPostStartInitializer implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(NflPopulationPostStartInitializer.class);
	
	@Autowired
	private NFLTeamManager teamManager;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		teamManager.initializeNFL();
	}

}
