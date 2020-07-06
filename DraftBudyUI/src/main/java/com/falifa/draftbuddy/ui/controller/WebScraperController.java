package com.falifa.draftbuddy.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.falifa.draftbuddy.ui.api.ApiDataDelegate;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;
import com.falifa.draftbuddy.ui.scraper.WebScraperManager;

@Controller
public class WebScraperController {
	
	private static final Logger log = LoggerFactory.getLogger(WebScraperController.class);
	
	@Autowired
	private WebScraperManager manager;
	
	@Autowired
	private JsonDataFileManager data;
	
	@Autowired
	private ApiDataDelegate apiDelegate;

    @RequestMapping(value = "/updateDataSourceFiles")
    public String updateDataSourceFiles() {
    	log.info("Updating data source files...");
    	if (manager.updateDataSources()) {
    		log.info("Data source files update SUCCESS");
    	} else {
    		log.error("Data source files update was not fully successful");
    	}
    	return "/home";
    }
    
    @RequestMapping(value = "/updateAll", method = RequestMethod.GET)
    public String updateData(Model model) {
    	log.info("Updating all player data...");
    	if (manager.updateAllData()) {
    		log.info("All player data update SUCCESS :: updated={}", String.valueOf(data.getPlayers().size()));
    	} else {
    		log.error("All player data update was not fully successful");
    	}
    	return "/home";
    }
    
    @RequestMapping(value = "/updateApi", method = RequestMethod.GET)
    public String updateApiData(Model model) {
    	log.info("Updating API data...");
    	log.info("API update = {}", apiDelegate.updateApiData());
    	return "/home";
    }
    
}
