package com.falifa.draftbuddy.api.data.builder;

import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {
	
	private static final String SEASON_YEAR = "2017";
	
	private static final String URL_BASE = "http://api.fantasy.nfl.com/v2";
	private static final String AND = "&";
	
	private static final String WEEK_STATS_BASE_PATH = "/players/weekstats?";
	private static final String META_DATA_BASE_PATH = "/player/ngs-content?";
	
	private static final String WEEK_PARAM = "week=";
	private static final String SEASON_PARAM = "season=";
	private static final String PLAYER_ID_PARAM = "playerId=";
	
	public String buildWeekStatsUrl(int week) {
		return new StringBuilder().append(URL_BASE).append(WEEK_STATS_BASE_PATH)
				.append(SEASON_PARAM).append(SEASON_YEAR)
				.append(AND).append(WEEK_PARAM).append(week)
				.toString();
	}

	public String buildMetaDataUrl(String playerId) {
		return new StringBuilder().append(URL_BASE).append(META_DATA_BASE_PATH)
				.append(AND).append(PLAYER_ID_PARAM).append(playerId)
				.toString();
	}

}
