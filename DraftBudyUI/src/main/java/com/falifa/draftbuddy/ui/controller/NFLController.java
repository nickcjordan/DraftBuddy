package com.falifa.draftbuddy.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falifa.draftbuddy.ui.manager.NFLTeamManager;
import com.falifa.draftbuddy.ui.model.player.Player;

@RestController
@RequestMapping("/nfl")
public class NFLController {
	
	private static final Logger log = LoggerFactory.getLogger(NFLController.class);

	@Autowired
	private NFLTeamManager nfl;
	
	@RequestMapping(value = "/player/{id}")
    public Player getPlayer(@PathVariable String id) {
    	log.info("Getting player with id={}...", id);
    	Player p = nfl.getPlayerById(id);
    	if (p != null) {
    		log.info("SUCCESS :: retrieved player={} :: id={}", p.getPlayerName(), p.getFantasyProsId());
    	} else {
    		log.error("Did not find player in NFLTeamManager map with id={}", id);
    	}
    	return p;
    }

}
