package com.falifa.draftbuddy.ui.draft.sleeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftPick;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftState;

@Component
public class SleeperAPIManager {
	
	
	private static final Logger log = LoggerFactory.getLogger(SleeperAPIManager.class);
	
	private static final String SLEEPER_DRAFT_ID_CURRENT_DEFAULT = "978319352829202432";

	
	private static final String SLEEPER_API_URL = "http://localhost:8081/api/draft";
	private RestTemplate restTemplate;

	public SleeperAPIManager() {
		this.restTemplate = new RestTemplate();
	}
	
	public SleeperDraftState getDraftState(String draftId) {
		if (StringUtils.isEmpty(draftId)) {
			draftId = SLEEPER_DRAFT_ID_CURRENT_DEFAULT;
		}
		SleeperDraftState response = null;
		try {
			response = restTemplate.getForObject(SLEEPER_API_URL + "?draftId=" + draftId, SleeperDraftState.class);
		} catch (Exception e) {
			log.error("ERROR calling Player Stats API for GET :: " + e.getMessage(), e);
		}
		
		return response;
	}
	
	public List<SleeperDraftPick> getDraftPicks(String draftId) {
		List<SleeperDraftPick> response = new ArrayList<>();
		try {
			String url = SLEEPER_API_URL + "/picks" + "?draftId=" + draftId;
			SleeperDraftPick[] r = new RestTemplate(new SimpleClientHttpRequestFactory()).getForEntity(url, SleeperDraftPick[].class).getBody();
			return Arrays.asList(r);
		} catch (Exception e) {
			log.error("ERROR calling Player Stats API for GET :: " + e.getMessage(), e);
		}
		return response;
	}
	
}
