package com.falifa.draftbuddy.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.falifa.draftbuddy.api.model.Player;

@RestController
public class DraftBuddyApiController {
	
	private static final Logger log = LoggerFactory.getLogger(DraftBuddyApiController.class);
	
	@Autowired
	private PlayerManager playerManager;
	
    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public List<Player> getAllPlayers() {
    	log.info("Getting all players...");
    	List<Player> players = playerManager.getAllPlayers();
    	log.info("Retrieved " + players.size() + " players");
    	return players;
    }

}
