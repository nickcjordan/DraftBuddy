package com.falifa.draftbuddy.ui.prep.scraper.webjson;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.FFBallersAPIData;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.FFBallersAPIDataWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FFBallersAPI {
	
	private static final String BALLERS_API_PROJECTIONS_URL = "https://www.thefantasyfootballers.com/wp-json/ffb/v1/udk/projections";

	public FFBallersAPIData getFFBallersData() {
		
		RestTemplate rt = new RestTemplate();
		
		HttpEntity<Void> request = new HttpEntity<>(buildHeaders());
		
		ResponseEntity<FFBallersAPIDataWrapper> data = rt.exchange(BALLERS_API_PROJECTIONS_URL, HttpMethod.GET, request, FFBallersAPIDataWrapper.class);
		
		FFBallersAPIData ob = null;
		try {
			ob = new ObjectMapper().readValue(data.getBody().getJson(), FFBallersAPIData.class);
		} catch (IOException e) {
			e.printStackTrace(); // TODO
		}
		
		
		return ob;
	}

	private HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Agent", "Mozilla");
		return headers;
	}

}
