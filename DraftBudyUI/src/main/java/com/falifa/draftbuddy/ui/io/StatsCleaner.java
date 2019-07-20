package com.falifa.draftbuddy.ui.io;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PLAYERNOTES_CUSTOM_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

import com.falifa.draftbuddy.ui.Log;
import com.falifa.draftbuddy.ui.comparator.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.model.NFL;
import com.falifa.draftbuddy.ui.model.Player;

public class StatsCleaner {
	
	private static final String HEADER = "#	RISK(\"!\"), SLEEPER(\"$\"), ROOKIE(\"R\"), NEW_TEAM(\"@\"), FAVORITE(\"*\"), RISING(\"+\"), FALLING(\"-\"), INJURY_RISK(\"i\"), BUST(\"B\")";

	static String first = null;
	private static DataFileReader dataReader = new DataFileReader();
	
	public static void cleanupTags() {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			for (List<String> split : dataReader.getSplitLinesFromFile(TAGS_CUSTOM_PATH, true, ",")) {
				try {
					Player p = NFL.getPlayer(split.get(0));
					sorted.add(p);
				} catch (Exception e) {
					Log.err("ERROR when cleaning up tags :: Player not found in NFL player list: " + split.get(0));
				}
			}
			
			StringBuilder sb = new StringBuilder(HEADER + "\n");
		    for (Player p : sorted) {
			    sb.append(p.getPlayerName() + "," + p.getTags() + "\n");
		    }
		    
		    BufferedWriter out = new BufferedWriter(new FileWriter(TAGS_CUSTOM_PATH));
		    out.write(sb.toString());
		    out.flush();
		    out.close();
		} catch (Exception ex) {
			Log.err(ex.getMessage());
		}
	}
	
	
	public static void cleanupNickNotes() {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			for (List<String> split : dataReader.getSplitLinesFromFile(PLAYERNOTES_CUSTOM_PATH, false, ",")) {
				
				Player p = NFL.getPlayer(split.get(0));
				try {
					p.addNicksNotes(split.get(1));
				} catch (Exception e) {
					Log.err("StatsCleaner.cleanupNickNotes() :: Error trying to cleanup Nick Notes: " + p.getPlayerName());
				}
				sorted.add(p);
			}
			
		    StringBuilder sb = new StringBuilder();
		    for (Player p : sorted) {
			    sb.append(p.getPlayerName() + "\",\"" + p.getNickNotes() + "\n");
		    }
		    
		    BufferedWriter out = new BufferedWriter(new FileWriter(PLAYERNOTES_CUSTOM_PATH));
		    out.write(sb.toString());
		    out.flush();
		    out.close();
		} catch(Exception ex) {
			Log.err(ex.getMessage());
		}
	}
	
	
	
	
	
	
}
