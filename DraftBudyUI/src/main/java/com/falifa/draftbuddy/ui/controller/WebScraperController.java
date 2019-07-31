package com.falifa.draftbuddy.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;
import com.falifa.draftbuddy.ui.scraper.WebScraperManager;

@Controller
public class WebScraperController {
	
	private static final Logger log = LoggerFactory.getLogger(WebScraperController.class);
	
	@Autowired
	private WebScraperManager manager;
	
	@Autowired
	private JsonDataFileManager data;

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
    
}
