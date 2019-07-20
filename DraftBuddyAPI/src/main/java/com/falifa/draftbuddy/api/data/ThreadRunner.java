package com.falifa.draftbuddy.api.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.falifa.draftbuddy.api.data.model.PlayerMetaData;
import com.falifa.draftbuddy.api.model.Player;

public class ThreadRunner implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(ThreadRunner.class);
	
	private Player player;
	private List<Player> playersToRemove;
	private String baseUrl;
	
	public ThreadRunner(List<Player> playersToRemove, Player player, String baseUrl) {
		this.playersToRemove = playersToRemove;
		this.player = player;
		this.baseUrl = baseUrl;
	}

	@Override
	public void run() {
		PlayerMetaData data = new PlayerMetadataAPIDelegate().retrieveMetaDataForPlayer(baseUrl + player.getPlayerId());
		if ((data != null) && isFantasyPosition(player, data.getPosition())) {
			populatePlayerWithMetaData(player, data);
		} else {
			playersToRemove.add(player);
		}
	}
	
	private boolean isFantasyPosition(Player p, String position) {
		boolean yes = (position.equals("QB") | position.equals("RB") | position.equals("WR") | position.equals("TE") | position.equals("K") | position.equals("DEF"));
//		return (position.equals("QB") | position.equals("RB") | position.equals("WR") | position.equals("TE") | position.equals("K"));
		if (!yes) {
			log.info("Found unwanted position :: {} :: player = {} : {}", position, p.getPlayerId(), p.getPlayerName());
		}
		return yes;
	}

	private void populatePlayerWithMetaData(Player player, PlayerMetaData data) {
		player.setByeWeek(data.getByeWeek());
		player.setImageUrl(data.getImageUrl());
		player.setNflTeamId(data.getNflTeamId());
		player.setPlayerName(data.getName());
		player.setPlayerPosition(data.getPosition());
		player.setSmallImageUrl(data.getSmallImageUrl());
		player.setTeamName(data.getNflTeamAbbr());
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
