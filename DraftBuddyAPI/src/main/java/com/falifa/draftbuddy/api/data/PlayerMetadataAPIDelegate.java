package com.falifa.draftbuddy.api.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.falifa.draftbuddy.api.data.builder.UrlBuilder;
import com.falifa.draftbuddy.api.data.model.PlayerMetaData;
import com.falifa.draftbuddy.api.data.model.RawPlayerStats;
import com.falifa.draftbuddy.api.data.model.WeekStatsResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerMetadataAPIDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMetadataAPIDelegate.class);
	
	public PlayerMetaData retrieveMetaDataForPlayer(String url) {
		try {
			return new RestTemplate(new SimpleClientHttpRequestFactory()).getForObject(url, PlayerMetaData.class);
		} catch (Exception e) {
			log.error("ERROR :: " + e.getLocalizedMessage() + " : "+ e.getMessage() + " :: Failed to get data for player from url " + url);
			return null;
		}
	}

}
