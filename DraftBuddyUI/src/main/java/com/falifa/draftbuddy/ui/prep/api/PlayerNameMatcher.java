package com.falifa.draftbuddy.ui.prep.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.apache.commons.text.similarity.LevenshteinDistance;

@Component
public class PlayerNameMatcher {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerNameMatcher.class);
	
	private Map<String, String> playerAlternateNamesToIdsMap;
	private Map<String, String> playerNamesToIdsMap;
	private Map<String, String> playerAlternateNamesToOriginalNamesMap;

	public PlayerNameMatcher() {
		this.playerAlternateNamesToIdsMap = new HashMap<String, String>();
		this.playerNamesToIdsMap = new HashMap<String, String>();
		this.playerAlternateNamesToOriginalNamesMap = new HashMap<String, String>();
	}
	
	public void addAlternateNames(String fullName, String id) {
		String alternateNameKey = buildAlternateNameKey(fullName);
		String nameKey = buildNameKey(fullName);
		playerAlternateNamesToIdsMap.put(alternateNameKey, id);
		playerAlternateNamesToOriginalNamesMap.put(alternateNameKey, fullName);
		playerNamesToIdsMap.put(nameKey, id);
	}
	
	public void addNameMapping(String val, String id) {
		playerNamesToIdsMap.put(val, id);
	}

	private String buildNameKey(String fullName) {
		String filteredFirst = getFilteredFirstName(fullName);
		return  filteredFirst + getFilteredLastName(fullName);
	}
	
	private String buildAlternateNameKey(String fullName) {
		String filteredFirst = getFilteredFirstName(fullName);
		int firstPartLength = 3;
		String firstPart = filteredFirst.length() >= firstPartLength ?  filteredFirst.substring(0, 3) : filteredFirst;
		return  firstPart + getFilteredLastName(fullName);
	}
	
	public String getFilteredFirstName(String fullName) {
		return filter(getFirstName(fullName));
	}

	public String getFilteredLastName(String fullName) {
		return filter(getLastName(fullName));
	}

	public String getFirstName(String fullName) {
		return fullName.split(" ")[0];
	}

	public String getLastName(String fullName) {
		return fullName.split(" ")[1];
	}

	public String findIdForClosestMatchingName(String fullName) {
		String id = null;
		String original = null;
		String filteredOriginal = null;
		String filteredLookingFor = null;
		Integer levenshteinDistance = null;
		String alternate = null;
		String firstId = null;
		try {
			firstId = playerNamesToIdsMap.get(buildNameKey(fullName));
			alternate = buildAlternateNameKey(fullName);
			id = playerAlternateNamesToIdsMap.get(alternate);
			original = playerAlternateNamesToOriginalNamesMap.get(alternate);
			filteredOriginal = getFilteredFirstName(original) + getFilteredLastName(original);
			filteredLookingFor = getFilteredFirstName(fullName) + getFilteredLastName(fullName);
			levenshteinDistance = new LevenshteinDistance(5).apply(filteredLookingFor, filteredOriginal);
			if (id != null) {
				log.debug("Found player in alternate name list :: levenshteinDistance={} :: id={} :: key found={} :: INQUIRED[name={}, filtered={}] :: FOUND[name={}, filtered={}]", levenshteinDistance, id, alternate, fullName, filteredLookingFor, original, filteredOriginal);
			}
		} catch (NullPointerException e) {
			log.debug("NULL POINTER trying to find player in alternate name list :: levenshteinDistance={} :: id={} :: key found={} :: INQUIRED[name={}, filtered={}] :: FOUND[name={}, filtered={}]", levenshteinDistance, id, alternate, fullName, filteredLookingFor, original, filteredOriginal);
		}
		return firstId != null ? firstId : id;
	}
	
	public String filter(String playerName) {
		return playerName.toUpperCase().replace("'", "").replace(" ", "").replace(".", "");
	}

}
