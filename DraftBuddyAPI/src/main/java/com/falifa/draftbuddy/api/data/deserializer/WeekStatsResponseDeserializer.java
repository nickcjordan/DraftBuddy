package com.falifa.draftbuddy.api.data.deserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;

import com.falifa.draftbuddy.api.data.model.RawPlayerStats;
import com.falifa.draftbuddy.api.data.model.WeekStatsResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class WeekStatsResponseDeserializer extends JsonDeserializer<WeekStatsResponse> {
	
	@Value("${data.year}")
	private String year;

	@Override
	public WeekStatsResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return buildPlayerStats(((JsonNode) p.getCodec().readTree(p)).get("games").get("102018").get("players").fields());
	}

	private WeekStatsResponse buildPlayerStats(Iterator<Entry<String, JsonNode>> playerIterator) {
		Map<String, RawPlayerStats> stats = new HashMap<String, RawPlayerStats>();
		while(playerIterator.hasNext()) {
			try {
				Entry<String, JsonNode> playerEntry = playerIterator.next();
				JsonNode weekNumberNode = playerEntry.getValue().get("stats").get("week").get(year);
				String weekNumber = weekNumberNode.fieldNames().next();
				RawPlayerStats stat = buildIndividualPlayerStats(playerEntry.getKey(), weekNumber, weekNumberNode.get(weekNumber).fields());
				stats.put(playerEntry.getKey(), stat);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new WeekStatsResponse(stats);
	}

	private RawPlayerStats buildIndividualPlayerStats(String playerId, String weekNumber, Iterator<Entry<String, JsonNode>> statSetIterator) {
		Map<String, String> statSetMap = new HashMap<String, String>();
		while(statSetIterator.hasNext()) {
			Entry<String, JsonNode> statSetEntry = statSetIterator.next();
			statSetMap.put(statSetEntry.getKey(), statSetEntry.getValue().asText());
		}
		return new RawPlayerStats(playerId, weekNumber, statSetMap);
	}

}
