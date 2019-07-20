package com.draftbuddy.io;

import static com.draftbuddy.constants.DataSourcePaths.PLAYER_NOTES_HTML_PATH;
import static com.draftbuddy.constants.DataSourcePaths.PPR_RANKINGS_HTML_PATH;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.draftbuddy.Log;
import com.draftbuddy.builder.TeamBuilder;
import com.draftbuddy.model.NFL;
import com.draftbuddy.model.Player;
import com.draftbuddy.model.Team;
import com.jaunt.Element;
import com.jaunt.UserAgent;

public class HTMLParser {

	public void addNotesFromHtml() {
		Element errorElement = null;
		UserAgent u = new UserAgent();
		try {
			u.open(new File(PLAYER_NOTES_HTML_PATH));
			Element notesWrapper = u.doc.findFirst("<div id=\"notes-wrapper\">");
			for (Element textElement : notesWrapper.findEvery("<td class=\"text\">")) {
				errorElement = textElement;
				String name = textElement.findFirst("<span class=\"title\">").findFirst("<a href>").getTextContent();
				System.out.println("adding notes for: " + name);
				name = name.replaceAll("\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t", " ");
				Player p = null;
				try {
					p = NFL.getPlayer(name);
				} catch (Exception e) {
					String[] tName = name.split(" ");
					String mascot = tName[tName.length - 1];
					try {
						String shorty = TeamBuilder.getTeamNameByMascot(mascot);
						if (shorty == null) { // empty line
							continue;
						}
						p = NFL.getPlayer(TeamBuilder.getTeamNameByMascot(mascot));
					} catch (Exception ex) {
						System.out.println();
						ex.printStackTrace();
					}
				}
				String notes = textElement.findFirst("<div class=\"player-note\">").getTextContent();
				notes = notes.replaceAll("\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t", " ");
				if (notes != null && !notes.equals("null")) {
					p.addAdditionalNotes(notes);
				} else {
					System.out.println();
				}
			}
		} catch (Exception e) {
			Log.err("Could not add notes from HTML: " + e.getMessage());
		} 
	}

	public List<Player> getPlayersFromHtml() {
		int currentTier = 0;
		List<Player> players = new ArrayList<Player>();
		Map<String, String> tierPositionMap = new HashMap<String, String>();
		int tierStartIndex = 1;
		UserAgent u = new UserAgent();
		try {
			String name = "";
			u.open(new File(PPR_RANKINGS_HTML_PATH));
			Element tbody = u.doc.findFirst("<tbody>");
			try {
				for (Element row : tbody.findEvery("<tr>")) { name = "";
					if (row.hasAttribute("style") && row.getAt("style").contains("display: none")) { 
						continue; 
					} // if empty first row, skip
					else if (row.hasAttribute("class") && row.getAt("class").contains("tier-row")) { // set tier number
						currentTier++;
						String tierText = row.findFirst("<td>").getTextContent();
						String tierNum = tierText.split(" ")[1];
						tierPositionMap.put(tierNum, String.valueOf(tierStartIndex));
					} 
					else if (row.hasAttribute("class") && row.getAt("class").contains("player-row")) { // build player
						Player p = new Player();
						tierStartIndex++;
						p.setTier(currentTier);
						Iterator<Element> iter = row.findEvery("<td>").iterator();
						if (iter.hasNext()) { p.setRank(iter.next().getTextContent()); }
						if (iter.hasNext()) { 
								iter.next();
						} 
						if (iter.hasNext()) { 
							Element namesElement = iter.next();
							String pName = namesElement.findFirst("<span class=\"full-name\">").getTextContent();
							if (pName.contains("(")) {
								if (pName.contains("Angeles")) {
									System.out.println();
								}
								String[] split = pName.split(" ");
								pName = split[split.length - 1];
								pName = pName.replace("(", "");
								pName = pName.replace(")", "");
								
								System.out.println();
							}
							p.setPlayerName(pName); 
							name = p.getPlayerName();
							try {
								p.setTeamName(namesElement.findFirst("<a href=\"/nfl/players/free-agents.php\">").getTextContent());
								System.out.println();
							} catch(Exception e) {
								try {
									p.setTeamName(namesElement.findFirst("<small class=\"grey\">").getTextContent());
									System.out.println();
								} catch (Exception e1) {
									p.setTeamName(p.extractTeamNameFromDSTPlayer(name));
									System.out.println();
								}
								
							}
						}
						if (iter.hasNext()) { p.setPosAndPosRank(iter.next().getTextContent()); }
						if (iter.hasNext()) { p.setBye(iter.next().getTextContent()); }
						if (iter.hasNext()) { p.setBest(iter.next().getTextContent()); }
						if (iter.hasNext()) { p.setWorst(iter.next().getTextContent()); }
						if (iter.hasNext()) { p.setAvg(iter.next().getTextContent()); }
						if (iter.hasNext()) { p.setStd_dev(iter.next().getTextContent()); }
						if (iter.hasNext()) { p.setAdp(p.getCorrectAdp(iter.next().getTextContent())); }
						if (iter.hasNext()) { p.setVersus(iter.next().getTextContent()); }
						p.finishBuildingPlayer();
						players.add(p);
						Log.info("Player built: " + p.getPlayerName() + " :: " + p.getId());
					} else {
						Log.info("No useful info found on <tr> line of html: " + row.toString());
					}
				}
			} catch (Exception e) {
				Log.err("Error building player from html:" + name + "\n" + e.getMessage());
			}
		} catch (Exception e) {
			Log.err("Could not add notes from HTML: " + e.getMessage());
		} 
		return players;
	}
}
