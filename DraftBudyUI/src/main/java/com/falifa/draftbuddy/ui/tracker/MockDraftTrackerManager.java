package com.falifa.draftbuddy.ui.tracker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.tracker.model.FFCAdpApiResponse;
import com.falifa.draftbuddy.ui.tracker.model.FFCPlayerTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaunt.Element;
import com.jaunt.ExpirationException;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

@Component
public class MockDraftTrackerManager {
	
	private static final Logger log = LoggerFactory.getLogger(MockDraftTrackerManager.class);
	
	@Value("${year}")
	private String year;
	
	private static final String FFC_URL_BASE = "https://fantasyfootballcalculator.com";
	private static final String FFC_QUERY_START = "/ajax/mockdraft/query.php?draft_id=";
	private static final String FFC_QUERY_END = "&last_pick=0&last_message=0&user_draft_spot=0";
	private static final String ID_MAP_FILE_PATH = "src/main/resources/ffc_player_id_mapping.json";
	private static final String FFC_ADP_API_URL = "http://fantasyfootballcalculator.com/api/v1/adp/ppr?teams=12&year=";
	
	@Autowired
	private DraftState draftState;
	
	@Autowired
	private MockDraftTrackerRunner trackerRunner;
	
	private Map<String, String> playerIdToNameMap;
	private ObjectMapper mapper;
	
	public MockDraftTrackerManager() {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			playerIdToNameMap = mapper.readValue(new File(ID_MAP_FILE_PATH), FFCIDTO.class).getMap();
		} catch (Exception e) {
			log.error("ERROR pulling FantasyFootballCalculator data from file", e);
			playerIdToNameMap = new HashMap<String, String>();
		}
	}
	
	public int addNewIds() {
		FFCAdpApiResponse response = getAdpDataFromApi();
		int newPlayerCount = addPlayersToMap(response);
		saveApiDataToJsonFile(newPlayerCount);
		log.info("Adding players to the mockPlayerIdMapping :: retrieved={} :: newlyAdded={}", Optional.ofNullable(response).map(r -> r.getPlayers().size()).orElse(0), newPlayerCount);
		return newPlayerCount;
	}

	private void saveApiDataToJsonFile(int newPlayerCount) {
		if (newPlayerCount > 0) {
			try {
				FFCIDTO wrapper = new FFCIDTO();
				wrapper.setMap(playerIdToNameMap);
				mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ID_MAP_FILE_PATH), wrapper);
			} catch (Exception e) {
				log.error("ERROR writing FantasyFootballCalculator mock draft id map to json file", e);
			}
		}
	}

	private int addPlayersToMap(FFCAdpApiResponse response) {
		int count = 0;
		if (response != null) {
			for (FFCPlayerTO p : response.getPlayers()) {
				if (!playerIdToNameMap.containsKey(p.getId())) {
					playerIdToNameMap.put(p.getId(), p.getName());
					count++;
				}
			}
		}
		return count;
	}

	private FFCAdpApiResponse getAdpDataFromApi() {
		FFCAdpApiResponse response = null;
		try {
			response = mapper.readValue(new UserAgent().sendGET(FFC_ADP_API_URL + year).getTextContent(), FFCAdpApiResponse.class);
		} catch (ResponseException | ExpirationException | IOException e) {
			log.error("ERROR calling FantasyFootballCalculator Adp API", e);
		}
		return response;
	}

	public void trackMockDraft(String mockDraftId, Model model) {
		Map<String, String> pickMap = buildPickMapFromXml(FFC_URL_BASE + FFC_QUERY_START + mockDraftId + FFC_QUERY_END);
		trackerRunner.trackMockDraft(pickMap, playerIdToNameMap, model);
	}
	
	private Map<String, String> buildPickMapFromXml(String url) {
		Map<String, String> picks = new ConcurrentHashMap<String, String>();
		UserAgent userAgent = new UserAgent();
		try {
			for (Element pick : userAgent.visit(url).findEvery("<pick>").toList()) {
				try {
					String id = pick.findFirst("player_id").getTextContent();
					String index = pick.getAt("id");
					if (!picks.containsKey(index)) {
						picks.put(index, id);
					} else {
						log.error("ERROR can not find index={} in map", index);
					}
				} catch (NotFound e) {
					log.error("ERROR could not find index or id :: " + pick.toString());
				}
			}
		} catch (ResponseException e) {
			log.error("ERROR making get all to " + url, e);
		} finally {
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return picks;
	}
	
	

}
