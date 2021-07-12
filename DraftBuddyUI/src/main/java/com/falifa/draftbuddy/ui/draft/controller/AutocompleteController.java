package com.falifa.draftbuddy.ui.draft.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;

@Controller
public class AutocompleteController {
	
	
	// this part works, but never got the autocomplete part in the UI to work
	
	@RequestMapping(value = "/autocomplete")
	public @ResponseBody List<String> getAutocompleteNames(@RequestParam("searchText") String searchText) {
		List<Player> players = NFLTeamManager.getAllAvailablePlayersByADP();
		return players.stream().map(x -> x.getPlayerName()).filter(x -> x.contains(searchText)).collect(Collectors.toList());
	}

}
