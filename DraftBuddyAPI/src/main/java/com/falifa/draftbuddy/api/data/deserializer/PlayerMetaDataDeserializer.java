package com.falifa.draftbuddy.api.data.deserializer;

import java.io.IOException;

import com.falifa.draftbuddy.api.data.model.PlayerMetaData;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class PlayerMetaDataDeserializer extends JsonDeserializer<PlayerMetaData> {

	@Override
	public PlayerMetaData deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		PlayerMetaData data = new PlayerMetaData();
		JsonNode node = jp.getCodec().readTree(jp);
		
		JsonNode details = node.get("games").elements().next().get("players").elements().next();
		
		data.setByeWeek(details.get("byeWeek").asText());
		data.setImageUrl(details.get("imageUrl").asText());
		data.setName(details.get("name").asText());
		data.setNflTeamAbbr(details.get("nflTeamAbbr").asText());
		data.setNflTeamId(details.get("nflTeamId").asText());
		data.setPlayerId(details.get("playerId").asText());
		data.setPosition(details.get("position").asText());
		data.setSmallImageUrl(details.get("smallImageUrl").asText());
		
		return data;
	}

}
