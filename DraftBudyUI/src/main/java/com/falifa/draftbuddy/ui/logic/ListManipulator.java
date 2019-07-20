package com.falifa.draftbuddy.ui.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.falifa.draftbuddy.ui.Log;
import com.falifa.draftbuddy.ui.comparator.PlayerADPComparator;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.controller.DraftController;
import com.falifa.draftbuddy.ui.model.NFL;
import com.falifa.draftbuddy.ui.model.Player;
import com.falifa.draftbuddy.ui.model.Team;

public class ListManipulator {
	
	List<Player> suggestions;
	private Team team;

	public ListManipulator(List<Player> suggestions, Team team) {
		this.suggestions = suggestions;
		this.team = team;
	}

	public void movePlayerUp(Player player) {
		int current = suggestions.indexOf(player);
		if (current == 0) return;
		
		Player other = suggestions.get(current - 1);
		
		if (withinTenPercent(player, other)) {
			suggestions.set(current, other);
			suggestions.set(current - 1, player);
		}
		
	}
	
	public void movePlayerDown(Player player) {
		int current = suggestions.indexOf(player);
		if (current == suggestions.size() - 1) return;
		
		Player other = suggestions.get(current + 1);
		
		if (withinTenPercent(player, other)) {
		suggestions.set(current, other);
		suggestions.set(current + 1, player);
		}
	}

	private boolean withinTenPercent(Player player, Player other) {
		return Math.abs(Integer.parseInt(player.getAdp()) - Integer.parseInt(other.getAdp())) <= 9;
	}
	
	void doTagLogic() {
		ArrayList<Player> copy = new ArrayList<Player>(suggestions);
		for (Player player : copy) {
			for (Tag tag : Tag.values()) {
				if (player.getTags().contains(tag.getTag())) {
					shiftPlayerByTag(tag, player);
				}
			}
		}
	}
	
	private void shiftPlayerByTag(Tag tag, Player player) {
		if (tag.getShift() > 0) {
			for (int i = 0; i < tag.getShift(); i++) {
				movePlayerUp(player);
			}
		} else if (tag.getShift() < 0) {
			for (int i = tag.getShift(); i < 0; i++) {
				movePlayerDown(player);
			}
		}
	}

	void removeTooFullPositions(Player player) {
		if (playerPositionIsFull(player) || isQbOrTeAndIsAlreadyPicked(player)) {
			suggestions.remove(player);
		}
	}

	private boolean isQbOrTeAndIsAlreadyPicked(Player player) {
		return (player.getPosition().equals(Position.QUARTERBACK) || (player.getPosition().equals(Position.TIGHTEND))) 
					&&	(team.getPlayersByPosition(player.getPosition()).size() > 0) // has at least 1 player?
					&&	(DraftController.getCurrentRoundNumber() < prop("te_qb_reintroduceRound")); // late enough in the draft for qb and te?
	}

	private boolean playerPositionIsFull(Player player) {
		return (team.getPlayersByPosition(player.getPosition()).size() >= prop(player.getPosition(), "Limit")); 
	}

	void removeTooEarlyPositions(Player player) {
		try {
			if (tooEarlyForPosition(player.getPosition())) {
				suggestions.remove(player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean tooEarlyForPosition(Position position) {
		return (DraftController.getCurrentRoundNumber() < prop(position, "Init"));
	}
	
	private int prop(Position pos, String prop) {
		try {
			return DraftController.get(pos.getAbbrev().toLowerCase() + prop);
		} catch (Exception e) {
			throw e;
		}
	}

	private int prop(String prop) {
		return DraftController.get(prop);
	}

	List<Player> checkForEmptyPositions() {
		List<Player> players = new ArrayList<Player>();
		for (Position position : Position.values()) {
			addIfPositionIsEmptyAndItIsLateEnoughToMatter(players, position, prop(position, "Warn"));
		}
		if (!players.isEmpty()) {
			Log.deb("checkForEmptyPositions : " + team.getName() + " has not filled all positions :: replacing suggestions with players from empty positions");
			Collections.sort(players, new PlayerADPComparator());
			return players;
		} else {
			return suggestions;
		}
	}
	
	private void addIfPositionIsEmptyAndItIsLateEnoughToMatter(List<Player> players, Position position, int roundToWorry) {
		if ((DraftController.getCurrentRoundNumber() >= DraftController.getNumberOfRounds() - roundToWorry) && team.getPlayersByPosition(position).isEmpty() ) {
			Log.deb("addIfPositionIsEmptyAndItIsLateEnoughToMatter " + team.getName() + " :: adding position = " + position.getName());
			players.addAll(NFL.getAvailablePlayersByPositionAsList(position));
		}
	}
	
}
