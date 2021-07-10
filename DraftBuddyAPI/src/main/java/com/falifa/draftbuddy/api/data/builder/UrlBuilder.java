package com.falifa.draftbuddy.api.data.builder;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlBuilder {
	
//	@Value("${data.year.previous}")
//	private String previousSeasonYear;
	
	private static final String URL_BASE = "https://api.fantasy.nfl.com/v2";
	private static final String AND = "&";
	
	private static final String WEEK_STATS_BASE_PATH = "/players/weekstats?";
	private static final String META_DATA_BASE_PATH = "/player/ngs-content?";
	
	private static final String WEEK_PARAM = "week=";
	private static final String SEASON_PARAM = "season=";
	private static final String PLAYER_ID_PARAM = "playerId=";
	
	public String buildWeekStatsUrl(int week) {
		return new StringBuilder().append(URL_BASE).append(WEEK_STATS_BASE_PATH)
				.append(SEASON_PARAM).append(LocalDate.now().minusYears(1).getYear())
				.append(AND).append(WEEK_PARAM).append(week)
				.toString();
	}

	public String buildMetaDataBaseUrl() {
		return new StringBuilder().append(URL_BASE).append(META_DATA_BASE_PATH)
				.append(AND).append(PLAYER_ID_PARAM)
				.toString();
	}

}
