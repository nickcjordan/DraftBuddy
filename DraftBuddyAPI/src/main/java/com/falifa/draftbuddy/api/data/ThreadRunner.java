package com.falifa.draftbuddy.api.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.falifa.draftbuddy.api.data.model.PlayerMetaData;
import com.falifa.draftbuddy.api.model.PlayerTO;

public class ThreadRunner implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(ThreadRunner.class);
	
	private PlayerTO player;
	private List<PlayerTO> playersToRemove;
	private String baseUrl;
	private PlayerMetadataAPIDelegate delegate;
	
	public ThreadRunner(List<PlayerTO> playersToRemove, PlayerTO player, String baseUrl) {
		this.playersToRemove = playersToRemove;
		this.player = player;
		this.baseUrl = baseUrl;
		this.delegate = new PlayerMetadataAPIDelegate();
	}

	@Override
	public void run() {
		PlayerMetaData data = delegate.retrieveMetaDataForPlayer(baseUrl + player.getPlayerId());
		if ((data != null) && isFantasyPosition(player, data.getPosition())) {
			populatePlayerWithMetaData(player, data);
		} else {
			playersToRemove.add(player);
		}
	}
	
	private boolean isFantasyPosition(PlayerTO p, String position) {
		boolean yes = (position.equals("QB") | position.equals("RB") | position.equals("WR") | position.equals("TE") | position.equals("K") | position.equals("DEF"));
//		return (position.equals("QB") | position.equals("RB") | position.equals("WR") | position.equals("TE") | position.equals("K"));
		if (!yes) {
//			log.info("Found unwanted position :: {} :: player = {} : {}", position, p.getPlayerId(), p.getPlayerName());
		}
		return yes;
	}

	private void populatePlayerWithMetaData(PlayerTO player, PlayerMetaData data) {
		player.setByeWeek(data.getByeWeek());
		player.setImageUrl(data.getImageUrl());
		player.setNflTeamId(data.getNflTeamId());
		player.setPlayerName(data.getName());
		player.setPlayerPosition(data.getPosition());
		player.setSmallImageUrl(data.getSmallImageUrl());
		player.setTeamName(data.getNflTeamAbbr());
	}
	
	public PlayerTO getPlayer() {
		return player;
	}

	public void setPlayer(PlayerTO player) {
		this.player = player;
	}

}
