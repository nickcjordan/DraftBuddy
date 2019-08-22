package com.falifa.draftbuddy.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.tracker.MockDraftTrackerManager;
import com.falifa.draftbuddy.ui.tracker.MockDraftTrackerRunner;

@Controller
@RequestMapping("/tracker")
public class MockDraftTrackerController {
	
	private static final Logger log = LoggerFactory.getLogger(MockDraftTrackerController.class);
	
	@Autowired
	private MockDraftTrackerManager tracker;
	
	@Autowired
	private MockDraftTrackerRunner trackerRunner; 
	
	@RequestMapping("/init")
	public int initializeIdMap() {
		log.info("Adding ID's from FantasyFootballCalculator API");
		return tracker.addNewIds();
	}
	
	@RequestMapping(path = "/track")
	public String startTrackingMockDraft(@RequestParam String mockDraftId, Model model) {
		log.info("Tracking mock draft with ID={}", mockDraftId);
		tracker.trackMockDraft(mockDraftId, model);
		return "pages/dashboardPage";
	}
	
	@RequestMapping(value = "/pick")
    public boolean doPickForDrafter(@RequestParam(defaultValue="") String name) {
		return trackerRunner.makePick(name);
    }

}
