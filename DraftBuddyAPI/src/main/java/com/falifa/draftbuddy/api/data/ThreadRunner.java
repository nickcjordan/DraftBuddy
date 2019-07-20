package com.falifa.draftbuddy.api.data;

import java.util.List;

import com.falifa.draftbuddy.api.data.model.PlayerMetaData;
import com.falifa.draftbuddy.api.model.Player;

public class ThreadRunner implements Runnable {
	
	private PlayerStatsAPIDelegate delegate;
	private Player player;
	private List<Player> playersToRemove;
	
	public ThreadRunner(List<Player> playersToRemove, PlayerStatsAPIDelegate delegate, Player player) {
		this.playersToRemove = playersToRemove;
		this.delegate = delegate;
		this.player = player;
	}

	@Override
	public void run() {
		PlayerMetaData data = delegate.retrieveMetaDataForPlayer(player.getPlayerId());
		if ((data != null) && isFantasyPosition(data.getPosition())) {
			populatePlayerWithMetaData(player, data);
		} else {
			playersToRemove.add(player);
		}
	}
	
	private boolean isFantasyPosition(String position) {
		return (position.equals("QB") | position.equals("RB") | position.equals("WR") | position.equals("TE") | position.equals("K"));
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
	
	public PlayerStatsAPIDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(PlayerStatsAPIDelegate delegate) {
		this.delegate = delegate;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}


}
