package com.falifa.draftbuddy.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.falifa.draftbuddy.ui.Log;
import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.exception.FalifaException;
import com.falifa.draftbuddy.ui.model.NFL;
import com.falifa.draftbuddy.ui.model.player.Player;

public class PlayerBuilder {
	
//	public static Player buildPlayer(List<String> split) {
//		String name = split.get(1);
//		if (name == null) { throw new RuntimeException("ERROR when trying to build player :: split.get(0) == null"); }
//		Player player =  null;
//		try {
//			player =  new Player(split);
//			Log.deb("PlayerBuilder.buildPlayer() :: PLAYER BUILT=[" + String.format("(%d) %s - %s", player.getId(), player.getPlayerName(), player.getTeamName()) + "]");
//		} catch (Exception e) {
//			Log.err("ERROR parsing line when building player [" + name + "] :: " + e.getMessage());
//		}
//		return player;
//	}
//	
//	public static void addNote(List<String> split) {
//		if (split.size() > 1) {
//			try {
//				Player p = NFL.getPlayer(split.get(0));
//				p.setNotes(buildNotes(split, 1));
//			} catch (Exception e) {
//				Log.err("ERROR could not add notes: PlayerBuilder.addnote() :: " + split.get(0) + "\n");
//			}
//		}
//	}
//
//	public static void addAdditionalNote(List<String> split) {
//		try {
//			NFL.getPlayer(split.get(0)).addAdditionalNotes(buildNotes(split, 1));
//		} catch (Exception e) {
//			Log.err(e.getMessage() + "... was trying to add additional notes.");
//		}
//	}
//
//	private static String buildNotes(List<String> split, int start) {
//		StringBuilder builder = new StringBuilder();
//		for (int i = start; i < split.size(); i++) {
//			String note = split.get(i) == null ? "" : split.get(i);
//			builder.append(note.trim());
//			if (i != split.size()-1) {
//				builder.append(" ");
//			}
//		}
//		return getPrettyNotes(builder.toString().split(" "));
//	}
//	
//	private static String getPrettyNotes(String[] words) {
//		int i = 0;
//		StringBuilder paragraph = new StringBuilder();
//		while (i < words.length) {
//			StringBuilder line = new StringBuilder();
//			while (line.length() < 100 && i < words.length) {
//				line.append(" " + words[i++]);
//			}
//			paragraph.append(line.toString() + "\n");
//		}
//		return paragraph.toString();
//	}
//
//	public static void addTag(List<String> split) throws FalifaException {
//		Player player = NFL.getPlayer(split.get(0));
//		String newTags = player.getTags()+split.get(1);
//		player.setTags(newTags);
//		ArrayList<String> tags = new ArrayList<String>();
//		for (int i = 0; i < newTags.length(); i++) {
//			tags.add(Tag.getIconClassFromTag(newTags.charAt(i)));
//		}
//		player.setIcons(tags);
//		System.out.println();
//	}
//
//	public static void addOLineRankings(List<String> split) {
//		Player player = NFL.getPlayer(split.get(0));
//		player.setOline_rank(split.get(1));
//		player.setOline_passScore(split.get(2));
//		player.setOline_runScore(split.get(3));
//		player.setOline_avgScore(split.get(4));
//	}
//
//	public static void addPlayerTargets(List<String> split) {
//		Player player = NFL.getPlayer(split.get(0));
//		player.setTotalTargets(split.get(1));
//		player.setAvgTargets(split.get(2));
//	}
//
//	public static void addPlayerPicLinks(List<String> split) {
//		Player player = NFL.getPlayer(split.get(0));
//		player.setPicLink(split.get(1));
//		player.setSmallPicLink(split.get(2));
//		String path = "/images/players/" + player.getPlayerName().replace(" ", "_") + "_photo.jpg";
//		player.setPicLocation(path);
//	}
//
//	public static void setPlayerAsATarget(String name) {
//		Player player = NFL.getPlayer(name);
//		player.setAsPlayerToTarget();
//	}
//
//	public static void setPlayerProjections(List<String> headers, List<String> split) {
//		Player player = NFL.getPlayer(split.get(0));
//		player.setProjectedPts(split.get(split.size() - 1));
//		LinkedHashMap<String, String> stats = new LinkedHashMap<String, String>();
//		for (int index = 2; index < headers.size() - 1; index++) { // only traversing stats not already extracted
//			stats.put(headers.get(index), split.get(index));
//		}
//		player.setProjectedStats(stats);
//	}
}
