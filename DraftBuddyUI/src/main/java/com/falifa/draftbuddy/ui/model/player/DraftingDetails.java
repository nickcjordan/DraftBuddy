package com.falifa.draftbuddy.ui.model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.prep.scraper.TeammateTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DraftingDetails {
	
	private int currentPlayerValue;
	private String currentPlayerValueBadgeClass;
	private String vsValueBadgeClass;
	private int roundDrafted;
	private boolean available;
	private boolean rookie;
	private boolean playerToTarget;
	private boolean handcuff;
	private String tags;
	private List<String> icons;
	private String handcuffs;
//	private List<Player> backups;
	private List<TeammateTO> positionTeammates;
	
	
	public DraftingDetails() {
		this.positionTeammates = new ArrayList<TeammateTO>();
//		this.backups = new ArrayList<Player>();
		this.icons = new ArrayList<String>();
		this.tags = "";
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
//	public List<Player> getBackups() {
//		return backups;
//	}
//	public void setBackups(List<Player> backups) {
//		this.backups = backups;
//		this.backups = this.backups.stream().distinct().collect(Collectors.toList());
//	}
//	@JsonIgnore
//	public void addBackup(Player backup) {
//		if (!this.backups.contains(backup)) {
//			this.backups.add(backup);
//		}
//		String newHandcuffs = this.backups.stream().map(p -> p.getPlayerName()).collect(Collectors.joining(", "));
//		this.handcuffs = Optional.ofNullable(newHandcuffs).orElse(backup.getPlayerName());
//	}
	
	public List<String> getIcons() {
		return icons;
	}
	public void setIcons(List<String> icons) {
		this.icons = icons;
	}
	@JsonIgnore
	public void addIcon(String icon) {
		this.icons.add(icon);
	}
	public int getCurrentPlayerValue() {
		return currentPlayerValue;
	}
	public void setCurrentPlayerValue(int currentPlayerValue) {
		this.currentPlayerValue = currentPlayerValue;
	}
	
	public String getCurrentPlayerValueBadgeClass() {
		return currentPlayerValueBadgeClass;
	}

	public void setCurrentPlayerValueBadgeClass(String currentPlayerValueBadgeClass) {
		this.currentPlayerValueBadgeClass = currentPlayerValueBadgeClass;
	}
	
	public String getVsValueBadgeClass() {
		return vsValueBadgeClass;
	}

	public void setVsValueBadgeClass(String vsValueBadgeClass) {
		this.vsValueBadgeClass = vsValueBadgeClass;
	}

	@JsonIgnore
	public void addTags(String newTags) {
		this.tags = !StringUtils.isEmpty(this.tags) ? this.tags + newTags : newTags;
		StringBuilder sb = new StringBuilder();
		tags.chars().distinct().forEach(c -> sb.append((char) c));
		tags = sb.toString();
		for (int i = 0; i < newTags.length(); i++) {
			String tagLocation = Tag.getIconClassFromTag(newTags.charAt(i));
			if (!this.icons.contains(tagLocation)) {
				this.icons.add(tagLocation);
			}
		}
	}
	
	@JsonIgnore
	public void removeTags(String newTags) {
		this.tags = !StringUtils.isEmpty(this.tags) ? this.tags : "";
		StringBuilder sb = new StringBuilder();
		tags.chars().distinct().forEach(c -> sb.append((char) c));
		tags = sb.toString();
		if (tags.contains(newTags)) {
			tags = tags.replace(newTags, "");
		}
		for (int i = 0; i < newTags.length(); i++) {
			String tagLocation = Tag.getIconClassFromTag(newTags.charAt(i));
			if (this.icons.contains(tagLocation)) {
				this.icons.remove(tagLocation);
			}
		}
	}

	public List<TeammateTO> getPositionTeammates() {
		return positionTeammates;
	}

	public void setPositionTeammates(List<TeammateTO> positionTeammates) {
		this.positionTeammates = positionTeammates;
	}

	
}
