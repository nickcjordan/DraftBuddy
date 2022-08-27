package com.falifa.draftbuddy.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.falifa.draftbuddy.api.model.PlayerMapResponse;
import com.falifa.draftbuddy.api.model.PlayerTO;
import com.falifa.draftbuddy.api.sleeper.SleeperAPI;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraft;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftPick;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftState;

@RestController
@RequestMapping("/api")
public class DraftBuddyApiController {
	
	private static final Logger log = LoggerFactory.getLogger(DraftBuddyApiController.class);
	
	@Autowired
	private PlayerManager playerManager;
	
	@Autowired
	private PlayerDataStorage data;
	
	@Autowired
	private SleeperAPI sleeper;
	
	@RequestMapping(value = "/players", method = RequestMethod.GET)
    public List<PlayerTO> getAllPlayers() {
    	log.info("Getting all players...");
    	List<PlayerTO> players = data.getAllPlayers();
    	log.info("Retrieved " + players.size() + " players");
    	return players;
    }
	
	@RequestMapping(value = "/players/map", method = RequestMethod.GET)
    public PlayerMapResponse getAllPlayersMap() {
    	log.info("Getting map of all players...");
    	Map<String, PlayerTO> players = data.getPlayerNameMap();
    	log.info("Retrieved " + players.size() + " players");
    	return new PlayerMapResponse(players);
    }
	
	@RequestMapping(value = "/player", method = RequestMethod.GET)
    public PlayerTO getPlayer(@RequestParam(name = "id", required = false) String id, @RequestParam(name = "name", defaultValue = "Tom Brady") String name) {
    	log.info("Getting player = {} : {}", id, name);
    	PlayerTO player = (id != null) ? data.getPlayerById(id) : data.getPlayerByName(name);
    	log.info("Retrieved player = {} : {}", Optional.ofNullable(player).map(p -> p.getPlayerId()).orElse("NULL"), Optional.ofNullable(player).map(p -> p.getPlayerName()).orElse("NULL"));
    	return player;
    }
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
    public boolean updateAllPlayers() {
    	log.info("Updating all player data from API...");
    	return playerManager.updateAllPlayers();
    }
	
    @RequestMapping(value = "/draft/picks", method = RequestMethod.GET)
    public List<SleeperDraftPick> getDraftPicks(@RequestParam(name = "draftId", required = false, defaultValue = "857115695748603904") String draftId) {
    	log.info("Getting Sleeper draft picks for draft id = {}...", draftId);
    	List<SleeperDraftPick> picks = sleeper.getDraftPicks(draftId);
    	log.info("Retrieved {} draft picks from Sleeper for draft id = {}", picks.size(), draftId);
    	return picks;
    }
    
    @RequestMapping(value = "/draft", method = RequestMethod.GET)
    public SleeperDraftState getDraft(@RequestParam(name = "draftId", required = false, defaultValue = "857115695748603904") String draftId) {
    	log.info("Getting Sleeper draft state for draft id = {}...", draftId);
    	SleeperDraftState draft = sleeper.getDraftState(draftId);
    	log.info("Built draft state from Sleeper response for draft id = {}", draftId);
    	return draft;
    }
    
}
