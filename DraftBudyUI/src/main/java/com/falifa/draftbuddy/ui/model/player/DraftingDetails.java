package com.falifa.draftbuddy.ui.model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DraftingDetails {
	
	private int currentPlayerValue;
	private int roundDrafted;
	private boolean available;
	private boolean rookie;
	private boolean playerToTarget;
	private boolean handcuff;
	private String tags;
	private List<String> icons;
	private String handcuffs;
	private List<Player> backups;
	
	public DraftingDetails() {
		this.backups = new ArrayList<Player>();
		this.icons = new ArrayList<String>();
	}
	
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isRookie() {
		return rookie;
	}
	public void setRookie(boolean rookie) {
		this.rookie = rookie;
	}
	public boolean isPlayerToTarget() {
		return playerToTarget;
	}
	public void setPlayerToTarget(boolean playerToTarget) {
		this.playerToTarget = playerToTarget;
	}
	public boolean isHandcuff() {
		return handcuff;
	}
	public void setHandcuff(boolean handcuff) {
		this.handcuff = handcuff;
	}

	public int getRoundDrafted() {
		return roundDrafted;
	}
	public void setRoundDrafted(int roundDrafted) {
		this.roundDrafted = roundDrafted;
	}
	public String getHandcuffs() {
		return handcuffs;
	}
	public void setHandcuffs(String handcuffs) {
		this.handcuffs = handcuffs;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public List<Player> getBackups() {
		return backups;
	}
	public void setBackups(List<Player> backups) {
		this.backups = backups;
	}
	public void addBackup(Player backup) {
		this.backups.add(backup);
		this.handcuffs = Optional.ofNullable(handcuffs).map(str -> str + ", " + backup.getPlayerName()).orElse(backup.getPlayerName());
	}
	public List<String> getIcons() {
		return icons;
	}
	public void setIcons(List<String> icons) {
		this.icons = icons;
	}
	public void addIcon(String icon) {
		this.icons.add(icon);
	}

	public int getCurrentPlayerValue() {
		return currentPlayerValue;
	}

	public void setCurrentPlayerValue(int currentPlayerValue) {
		this.currentPlayerValue = currentPlayerValue;
	}

	public boolean isAPlayerToTarget() {
		return playerToTarget;
	}

	public void setAsPlayerToTarget(boolean playerToTarget) {
		this.playerToTarget = playerToTarget;
	}

	public boolean isAHandcuff() {
		return handcuff;
	}

	public void setAsHandcuff(boolean handcuff) {
		this.handcuff = handcuff;
	}
	
}
