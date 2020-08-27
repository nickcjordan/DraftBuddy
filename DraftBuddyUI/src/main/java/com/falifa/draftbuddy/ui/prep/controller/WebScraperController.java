package com.falifa.draftbuddy.ui.prep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.falifa.draftbuddy.ui.prep.api.ApiDataDelegate;
import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.prep.scraper.WebScraperManager;

@Controller
public class WebScraperController {
	
	private static final Logger log = LoggerFactory.getLogger(WebScraperController.class);
	
	@Autowired
	private WebScraperManager manager;
	
	@Autowired
	private ApiDataDelegate apiDelegate;
	
	@Autowired
	private DraftState draftState;

    @RequestMapping(value = "/updateDataSourceFiles")
    public String updateDataSourceFiles() {
    	log.info("Updating data source files...");
    	if (manager.updateDataSources()) {
    		log.info("Data source files update SUCCESS");
    	} else {
    		log.error("Data source files update was not fully successful");
    	}
    	NFLTeamManager.initializeNFL();
    	draftState.initializeDraft();
    	return "/home";
    }
    
    @RequestMapping(value = "/updateAll", method = RequestMethod.GET)
    public String updateData(Model model) {
    	log.info("Updating all player data...");
    	if (manager.updateAllData()) {
    		log.info("All player data update SUCCESS :: updated={}", String.valueOf(PlayerCache.getPlayers().size()));
    	} else {
    		log.error("All player data update was not fully successful");
    	}
    	NFLTeamManager.initializeNFL();
    	draftState.initializeDraft();
    	return "/home";
    }
    
    @RequestMapping(value = "/updateApi", method = RequestMethod.GET)
    public String updateApiData(Model model) {
    	log.info("Updating API data...");
    	log.info("API update = {}", apiDelegate.updateApiData());
    	return "/home";
    }
    
}
