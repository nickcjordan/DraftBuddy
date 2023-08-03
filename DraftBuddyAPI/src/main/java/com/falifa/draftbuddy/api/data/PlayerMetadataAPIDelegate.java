package com.falifa.draftbuddy.api.data;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.falifa.draftbuddy.api.data.model.PlayerMetaData;
import com.falifa.draftbuddy.api.data.model.PlayerMetaDataAPIError;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerMetadataAPIDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerMetadataAPIDelegate.class);
	
	public PlayerMetaData retrieveMetaDataForPlayer(String url) {
		try {
			return new RestTemplate(new SimpleClientHttpRequestFactory()).getForObject(url, PlayerMetaData.class);
		} catch (HttpClientErrorException e) {
				try {
					PlayerMetaDataAPIError error = new ObjectMapper().readValue(e.getResponseBodyAsString(), PlayerMetaDataAPIError.class);
					if (!error.getErrors().get(0).getMessage().equals("PLAYER_INVALID")) {
						log.error("ERROR :: " + error.getErrors().get(0).getMessage() + " :: " + e.getLocalizedMessage() + " : "+ e.getMessage() + " :: Failed to get data for player from url " + url);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			return null;
		}
	}

}
