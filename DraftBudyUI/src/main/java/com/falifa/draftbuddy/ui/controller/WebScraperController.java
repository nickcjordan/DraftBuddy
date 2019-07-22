package com.falifa.draftbuddy.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falifa.draftbuddy.ui.scraper.WebScraperManager;

@RestController
@RequestMapping("/ui")
public class WebScraperController {
	
	private static final Logger log = LoggerFactory.getLogger(WebScraperController.class);
	
	@Autowired
	private WebScraperManager manager;

    @RequestMapping(value = "/updateDataSourceFiles")
    public String updateDataSourceFiles() {
    	log.info("Updating data source files...");
    	if (manager.updateDataSources()) {
    		log.info("Data source files update SUCCESS");
    		return "SUCCESS";
    	} else {
    		log.error("Data source files update was not fully successful");
    		return "ERROR";
    	}
    }
    
    @RequestMapping(value = "/updateAll")
    public String updateData() {
    	log.info("Updating all player data...");
    	if (manager.updateAllData()) {
    		log.info("All player data update SUCCESS");
    		return "SUCCESS";
    	} else {
    		log.error("All player data update was not fully successful");
    		return "ERROR";
    	}
    }
    
}
