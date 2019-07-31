package com.falifa.draftbuddy.ui.constants;

import com.falifa.draftbuddy.ui.model.player.Player;

public enum Tag {

	RISK("!", 0, "glyphicon glyphicon-exclamation-sign"),
	SLEEPER("$", 1, "glyphicon glyphicon-usd"),
	ROOKIE("R", 0, "glyphicon glyphicon-registration-mark"),
	NEW_TEAM("@", 0, "glyphicon glyphicon-share-alt"),
	FAVORITE("*", 3, "glyphicon glyphicon-thumbs-up"),
	RISING("+", 1, "glyphicon glyphicon-arrow-up"),
	FALLING("-", -1, "glyphicon glyphicon-arrow-down"),
	INJURY_RISK("i", -1, "glyphicon glyphicon-wrench"),
	BUST("B", -2, "glyphicon glyphicon-ban-circle");
	
	private String tag;
	private int shift;
	private String icon;
	
	Tag(String tag, int shift, String icon) {
		this.tag = tag;
		this.shift = shift;
		this.setIcon(icon);
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public static boolean isRookie(Player player) {
		return player.getDraftingDetails().getTags().contains(ROOKIE.getTag());
	}
	
	public static boolean isSleeper(Player player) {
		return player.getDraftingDetails().getTags().contains(SLEEPER.getTag());
	}
	
	public static boolean isRisk(Player player) {
		return player.getDraftingDetails().getTags().contains(RISK.getTag());
	}
	
	public static boolean isNewTeam(Player player) {
		return player.getDraftingDetails().getTags().contains(NEW_TEAM.getTag());
	}
	
	public static boolean isFavorite(Player player) {
		return player.getDraftingDetails().getTags().contains(FAVORITE.getTag());
	}
	
	public static boolean isRising(Player player) {
		return player.getDraftingDetails().getTags().contains(RISING.getTag());
	}
	
	public static boolean isFalling(Player player) {
		return player.getDraftingDetails().getTags().contains(FALLING.getTag());
	}
	
	public static boolean isBust(Player player) {
		return player.getDraftingDetails().getTags().contains(BUST.getTag());
	}
	
	public static boolean isInjuryRisk(Player player) {
		return player.getDraftingDetails().getTags().contains(INJURY_RISK.getTag());
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public static String getIconClassFromTag(char c) {
		for (Tag t : Tag.values()) {
			if (t.getTag().equals(String.valueOf(c))) {
				return t.getIcon();
			}
		}
		return "";
	}
	
}
