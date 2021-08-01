package com.falifa.draftbuddy.ui.prep.data;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.model.RoundSpecificStrategy;
import com.falifa.draftbuddy.ui.model.player.Player;

@Component
public class StrategyFileHandler {
	
	
	private static final Logger log = LoggerFactory.getLogger(StrategyFileHandler.class);


	private static final String HEADER = "#	RISK(\"!\"), SLEEPER(\"$\"), ROOKIE(\"R\"), NEW_TEAM(\"@\"), FAVORITE(\"*\"), RISING(\"+\"), FALLING(\"-\"), INJURY_RISK(\"i\"), BUST(\"B\")";
	static String first = null;

	public List<List<String>> getSplitLinesFromFile(String fileName, boolean skipHeader) throws FileNotFoundException {
		List<List<String>> splitLinesList = new ArrayList<>();
		Scanner scanner = new Scanner(new File(fileName));
		if (skipHeader && scanner.hasNextLine()) {
			scanner.nextLine(); // move past header
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\""))) {
				List<String> split = splitAndCleanupLine(line, ",");
				splitLinesList.add(split);
			}
		}
		scanner.close();
		return splitLinesList;
	}

	private List<String> splitAndCleanupLine(String line, String regex) {
		List<String> splitLine = new ArrayList<String>();
		for (String text : line.split(regex)) {
			String edited = (text == null || StringUtils.isEmpty(text.trim()) || text.trim().equals("\"\"")) ? "-" : text.trim();
			splitLine.add(edited.replaceAll("\"", ""));
		}
		return splitLine;
	}

	public List<String> getLinesFromFile(String fileName) throws FileNotFoundException {
		List<String> names = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(fileName));
		if (scanner.hasNextLine()) {
			scanner.nextLine(); // move past header
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if ((!StringUtils.isEmpty(line)) && (!line.equals("\"\""))) {
				names.add(line);
			}
		}
		scanner.close();
		return names;
	}
	// RISK(\"!\"), SLEEPER(\"$\"), ROOKIE(\"R\"), NEW_TEAM(\"@\"), FAVORITE(\"*\"), RISING(\"+\"), FALLING(\"-\"), INJURY_RISK(\"i\"), BUST(\"B\")";
	public void addTagsToPlayer(Player player, String newTags) {
		player.getDraftingDetails().addTags(newTags);
		if (newTags.contains(Tag.FAVORITE.getTag())) {
			player.getDraftingDetails().setPlayerToTarget(true);
		}
		if (player.getDraftingDetails().isRookie()) {
			player.getDraftingDetails().addTags(Tag.ROOKIE.getTag());
		}
		if (player.getTeam().isNewCoach()) {
			player.getDraftingDetails().addTags(Tag.NEW_COACH.getTag());
		}
	}
	
	public void removeTagsFromPlayer(Player player, String removeTags) {
		player.getDraftingDetails().removeTags(removeTags);
		if (removeTags.contains(Tag.FAVORITE.getTag())) {
			player.getDraftingDetails().setPlayerToTarget(false);
		}
	}

	public void updateTagFileWithCleanedUpResults(TreeSet<Player> sorted) throws IOException {
		StringBuilder sb = new StringBuilder(HEADER + "\n");
		for (Player p : sorted) {
			String tags = p.getDraftingDetails().getTags();
			if (tags != null) {
				sb.append(p.getPlayerName() + "," + tags + "," + p.getFantasyProsId() + "\n");
			} else {
				sb.append(p.getPlayerName() + ",\n");
			}
		}
		if (sorted.size() > 0) {
			log.info("Rewrote tags file with {} players", sorted.size());
			BufferedWriter out = new BufferedWriter(new FileWriter(TAGS_CUSTOM_PATH));
			out.write(sb.toString());
			out.flush();
			out.close();
		} else {
			log.error("0 players found to write tags for :: skipping tag file rewrite...");
		}
	}

	public RoundSpecificStrategy buildRoundSpecificStrategy(List<String> split) {
		RoundSpecificStrategy strategy = new RoundSpecificStrategy();
		strategy.setRound(split.get(0));
		strategy.setStrategyText(split.get(1));
		strategy.setTargetPositions(split.get(2));
		strategy.setTargetPlayers(buildListOfTargetPlayers(split));
		return strategy;
	}

	private List<String> buildListOfTargetPlayers(List<String> split) {
		List<String> players = new ArrayList<String>();
		for (int i = 3; i < split.size(); i++) {
			players.add(split.get(i));
		}
		return players;
	}

	// public static void cleanupNickNotes() {
	// try {
	// TreeSet<Player> sorted = new TreeSet<Player>(new
	// AlphabetizedPlayerComparator());
	// for (List<String> split :
	// dataReader.getSplitLinesFromFile(PLAYERNOTES_CUSTOM_PATH, false, ",")) {
	//
	// Player p = NFL.getPlayer(split.get(0));
	// try {
	// p.addNicksNotes(split.get(1));
	// } catch (Exception e) {
	// Log.err("StatsCleaner.cleanupNickNotes() :: Error trying to cleanup Nick
	// Notes: " + p.getPlayerName());
	// }
	// sorted.add(p);
	// }
	//
	// StringBuilder sb = new StringBuilder();
	// for (Player p : sorted) {
	// sb.append(p.getPlayerName() + "\",\"" + p.getNickNotes() + "\n");
	// }
	//
	// BufferedWriter out = new BufferedWriter(new
	// FileWriter(PLAYERNOTES_CUSTOM_PATH));
	// out.write(sb.toString());
	// out.flush();
	// out.close();
	// } catch(Exception ex) {
	// Log.err(ex.getMessage());
	// }
	// }
	//

}
