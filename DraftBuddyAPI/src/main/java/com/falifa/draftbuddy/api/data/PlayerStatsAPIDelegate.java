package com.falifa.draftbuddy.api.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.falifa.draftbuddy.api.data.builder.UrlBuilder;
import com.falifa.draftbuddy.api.data.model.PlayerMetaData;
import com.falifa.draftbuddy.api.data.model.WeekStatsResponse;

@Component
public class PlayerStatsAPIDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerStatsAPIDelegate.class);
	
	@Autowired
	private UrlBuilder urls;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Cacheable("weekStatsResponse")
	public WeekStatsResponse retrieveAllWeekStats(int week) {
		String url = urls.buildWeekStatsUrl(week);
		log.info("Calling weeklyStats for week " + week + " :: " + url);
		WeekStatsResponse response = restTemplate.getForObject(url, WeekStatsResponse.class);
		response.setWeekNumber(week);
		return response;
	}
	
	public PlayerMetaData retrieveMetaDataForPlayer(String playerId) {
		try {
			return restTemplate.getForObject(urls.buildMetaDataUrl(playerId), PlayerMetaData.class);
		} catch (Exception e) {
			log.error("ERROR :: Failed to get data for player " + playerId);
			return null;
		}
	}
	


}
