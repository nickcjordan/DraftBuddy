package com.falifa.draftbuddy.api.sleeper;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.falifa.draftbuddy.api.sleeper.model.SleeperDraft;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftPick;
import com.falifa.draftbuddy.api.sleeper.model.SleeperUser;

@Component
public class SleeperDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(SleeperDelegate.class);
	
	public List<SleeperDraftPick> getDraftPicks(String draftId) {
		String url = "https://api.sleeper.app/v1/draft/" + draftId + "/picks";
		try {
			SleeperDraftPick[] response = new RestTemplate(new SimpleClientHttpRequestFactory()).getForEntity(url, SleeperDraftPick[].class).getBody();
			return Arrays.asList(response);
		} catch (Exception e) {
			log.error("ERROR :: " + e.getLocalizedMessage() + " : "+ e.getMessage() + " :: Failed to get Sleeper draft picks from url " + url);
			return null;
		}
	}
	
	public SleeperDraft getDraft(String draftId) {
		String url = "https://api.sleeper.app/v1/draft/" + draftId;
		try {
			SleeperDraft response = new RestTemplate(new SimpleClientHttpRequestFactory()).getForObject(url, SleeperDraft.class);
			return response;
		} catch (Exception e) {
			log.error("ERROR :: " + e.getLocalizedMessage() + " : "+ e.getMessage() + " :: Failed to get Sleeper draft from url " + url);
			return null;
		}
	}

	public List<SleeperUser> getUsersInLeague(String leagueId) {
		String url = "https://api.sleeper.app/v1/league/" + leagueId + "/users";
		
		try {
			List<SleeperUser> response = Arrays.asList(new RestTemplate(new SimpleClientHttpRequestFactory()).getForObject(url, SleeperUser[].class));
			return response;
		} catch (Exception e) {
			log.error("ERROR :: " + e.getLocalizedMessage() + " : "+ e.getMessage() + " :: Failed to get Sleeper users from url " + url);
			return null;
		}
	}
	
	public SleeperUser getUser(String userId) {
		String url = "https://api.sleeper.app/v1/user/" + userId;
		
		try {
			SleeperUser response = new RestTemplate(new SimpleClientHttpRequestFactory()).getForObject(url, SleeperUser.class);
			return response;
		} catch (Exception e) {
			log.error("ERROR :: " + e.getLocalizedMessage() + " : "+ e.getMessage() + " :: Failed to get Sleeper user from url " + url);
			return null;
		}
	}

}
