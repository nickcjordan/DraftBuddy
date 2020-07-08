package com.falifa.draftbuddy.ui.prep.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.model.FantasyFootballerPlayerTO;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

@Component
public class FantasyFootballersScraper {
	
	private static final Logger log = LoggerFactory.getLogger(FantasyFootballersScraper.class);


	public List<FantasyFootballerPlayerTO> buildFullListOfUpdatesFromFantasyFootballers() {
		List<FantasyFootballerPlayerTO> tos = new ArrayList<FantasyFootballerPlayerTO>();
		tos.addAll(getListOfPlayersFromFantasyFootballersFile(FANTASYFOOTBALLERS_VALUES_PATH, Tag.VALUE));
		tos.addAll(getListOfPlayersFromFantasyFootballersFile(FANTASYFOOTBALLERS_BREAKOUTS_PATH, Tag.BREAKOUT));
		tos.addAll(getListOfPlayersFromFantasyFootballersFile(FANTASYFOOTBALLERS_BUSTS_PATH, Tag.BUST));
		tos.addAll(getListOfPlayersFromFantasyFootballersFile(FANTASYFOOTBALLERS_INJURIES_PATH, Tag.INJURY_RISK));
		tos.addAll(getListOfPlayersFromFantasyFootballersFile(FANTASYFOOTBALLERS_ROOKIES_PATH, Tag.ROOKIE));
		tos.addAll(getListOfPlayersFromFantasyFootballersFile(FANTASYFOOTBALLERS_SLEEPERS_PATH, Tag.SLEEPER));
		return tos;
	}
	
	private List<FantasyFootballerPlayerTO> getListOfPlayersFromFantasyFootballersFile(String path, Tag tag) {
		List<FantasyFootballerPlayerTO> tos = new ArrayList<FantasyFootballerPlayerTO>();
		for (Element e : pullElementsOutOfFile(path, "<div class=\"ffb-blurb--content\">")) {
			tos.add(buildFantasyFootballersTOFromElement(e, tag));
		}
		return tos;
	}

	private FantasyFootballerPlayerTO buildFantasyFootballersTOFromElement(Element element, Tag tag) {
		FantasyFootballerPlayerTO to = new FantasyFootballerPlayerTO();
		to.setTag(tag);
		to.setName(extractNameFromElement(element));
		to.setText(extractCommentFromElement(element));
		return to;
	}
	
	private String extractNameFromElement(Element element) {
		try {
			String text = element.getFirst("<h3>").getTextContent().trim();
			String name = text.substring(0, text.indexOf(","));
			return name;
		} catch (NotFound e) {
			e.printStackTrace(); // TODO
		}
		return null;
	}
	
	private String extractCommentFromElement(Element element) {
		try {
			return element.getFirst("<p>").getTextContent().replace("\"", "'").trim();
		} catch (NotFound e) {
			e.printStackTrace(); // TODO
		}
		return null;
	}
	
	private Elements pullElementsOutOfFile(String path, String query) {
		UserAgent userAgent = new UserAgent();
		try {
			userAgent.open(new File(path));
			return userAgent.doc.findEvery(query);
		} catch (Exception e) {
			log.error("ERROR parsing file at " + path, e);
		} finally {
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return null;
	}

}
