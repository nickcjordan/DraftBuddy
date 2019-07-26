package com.falifa.draftbuddy.ui.scraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.*;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.PPR_RANKINGS_HTML_PATH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.falifa.draftbuddy.ui.constants.DataSourcePaths;
import com.jaunt.Element;
import com.jaunt.UserAgent;


@Component
public class HtmlDataFileParser {
	
	
	private static final Logger log = LoggerFactory.getLogger(HtmlDataFileParser.class);


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


	public String parseTableDataFromFantasyProsPositionalProjections(String position) {
		return pullTableHtmlOutOfFile(DataSourcePaths.buildPositionalPath(position, FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_HTML_FILE_PATH), "<table id=\"data\">");
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
			try { userAgent.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return html;
	}

}
