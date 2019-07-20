package com.falifa.draftbuddy.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.falifa.draftbuddy.api.model.Player;

@RestController
public class DraftBuddyApiController {
	
	private static final Logger log = LoggerFactory.getLogger(DraftBuddyApiController.class);
	
	@Autowired
	private PlayerManager playerManager;
	
	@Autowired
	private PlayerDataStorage data;
	
	@RequestMapping(value = "/players", method = RequestMethod.GET)
    public List<Player> getAllPlayers() {
    	log.info("Getting all players...");
    	List<Player> players = data.getAllPlayers();
    	log.info("Retrieved " + players.size() + " players");
    	return players;
    }
	
	@RequestMapping(value = "/player", method = RequestMethod.GET)
    public Player getPlayer(@RequestParam(name = "id", required = false) String id, @RequestParam(name = "name", defaultValue = "Tom Brady") String name) {
    	log.info("Getting player = {} : {}", id, name);
    	Player player = (id != null) ? data.getPlayerById(id) : data.getPlayerByName(name);
    	log.info("Retrieved player = {} : {}", Optional.ofNullable(player).map(p -> p.getPlayerId()).orElse("NULL"), Optional.ofNullable(player).map(p -> p.getPlayerName()).orElse("NULL"));
    	return player;
    }
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
    public boolean updateAllPlayers() {
    	log.info("Updating all player data from API...");
    	return playerManager.updateAllPlayers();
    }
	
//    @RequestMapping(value = "/players", method = RequestMethod.GET)
//    public List<Player> getAllPlayers() {
//    	log.info("Getting all players...");
//    	List<Player> players = playerManager.getAllPlayers();
//    	log.info("Retrieved " + players.size() + " players");
//    	return players;
//    }

}
