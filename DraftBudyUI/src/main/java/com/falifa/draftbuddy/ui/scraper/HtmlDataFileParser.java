package com.falifa.draftbuddy.ui.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYFOOTBALLERS_VALUES_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_ADP_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_NOTES_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_RANKINGS_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_ROOKIES_RANKINGS_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_TARGET_LEADERS_HTML_FILE_PATH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.DataSourcePaths;
import com.falifa.draftbuddy.ui.model.to.FantasyFootballerPlayerTO;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.UserAgent;

@Component
public class HtmlDataFileParser {

	private static final Logger log = LoggerFactory.getLogger(HtmlDataFileParser.class);

	@Autowired
	private FantasyFootballersScraper ffScraper;

	public String parseTableDataFromFantasyProsRankings() {
		return pullTableHtmlOutOfFile(FANTASYPROS_RANKINGS_HTML_FILE_PATH, "<table id=\"rank-data\">");
	}

	public String parseTableDataFromFantasyProsADP() {
		return pullTableHtmlOutOfFile(FANTASYPROS_ADP_HTML_FILE_PATH, "<table id=\"data\"");
	}

	public String parseTableDataFromFantasyProsNotes() {
		return pullTableHtmlOutOfFile(FANTASYPROS_NOTES_HTML_FILE_PATH, "<div id=\"notes-wrapper\">");
	}

	public String parseTableDataFromFantasyProsRookiesRankings() {
		return pullTableHtmlOutOfFile(FANTASYPROS_ROOKIES_RANKINGS_HTML_FILE_PATH, "<table id=\"rank-data\">");
	}

	public String parseTableDataFromFantasyProsTargetLeaders() {
		return pullTableHtmlOutOfFile(FANTASYPROS_TARGET_LEADERS_HTML_FILE_PATH, "<table id=\"data\">");
	}

	public String parseTableDataFromFantasyProsPositionalProjections(String position) {
		return pullTableHtmlOutOfFile(DataSourcePaths.buildPositionalPath(position, FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_HTML_FILE_PATH), "<table id=\"data\">");
	}

	public List<FantasyFootballerPlayerTO> parseListOfPlayerDataFromFantasyFootballers() {
		List<FantasyFootballerPlayerTO> tos = ffScraper.buildFullListOfUpdatesFromFantasyFootballers();
		log.info("Got {} FantasyFootballTOs for updates", tos.size());
		return tos;
	}

	private String pullTableHtmlOutOfFile(String path, String query) {
		String html = null;
		UserAgent userAgent = new UserAgent();
		try {
			userAgent.open(new File(path));
			Element table = userAgent.doc.findFirst(query);
			html = table.outerHTML();
		} catch (Exception e) {
			log.error("ERROR parsing file at " + path, e);
		} finally {
			try {
				userAgent.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return html;
	}

}
