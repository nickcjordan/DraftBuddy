package com.falifa.draftbuddy.ui.prep.scraper;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.*;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_ADP_URL;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_NOTES_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_NOTES_URL;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_URL;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_RANKINGS_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_RANKINGS_URL;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_ROOKIES_RANKINGS_HTML_FILE_PATH;
import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.FANTASYPROS_ROOKIES_RANKINGS_URL;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.DataSourcePaths;
import com.falifa.draftbuddy.ui.constants.Position;

@Component
public class WebScraperManager {
	
	private static final Logger log = LoggerFactory.getLogger(WebScraperManager.class);
	
	@Autowired
	private DataConsolidator parserManager;
	
	@Autowired
	private JsonDataFileManager updater;
	
	public boolean updateAllData() {
		if (updateDataSources()) {
			log.info("Data source files updated :: beginning parsing...");
			if (parserManager.parseAllDataSources()) {
				log.info("Entire update process completed successfully");
				return true;
			} else {
				log.info("Update process finished with errors");
			}
		} else {
			log.error("ERROR :: not going to parse updated data sources...");
		}
			return false;
	}
	
	public boolean updateDataSources() {
		boolean success = true;
		success &= updateFantasyProsPPRRankingsDataSource();
		success &= updateFantasyProsADPDataSource();
		success &= updateFantasyProsNotesDataSource();
		success &= updateFantasyProsRookiesRankingsDataSource();
		success &= updateFantasyProsPositionalProjectionsDataSource();
		success &= updateFantasyProsTargetLeadersDataSource();
		success &= updateFantasyProsTeamTargetsDataSource();
		return success;
	}

	private boolean updateFantasyProsTeamTargetsDataSource() {
		return updater.downloadFileFromUrl(FANTASYPROS_TEAM_TARGETS_URL, FANTASYPROS_TEAM_TARGETS_HTML_FILE_PATH);
	}
	
	private boolean updateFantasyProsTargetLeadersDataSource() {
		return updater.downloadFileFromUrl(FANTASYPROS_TARGET_LEADERS_URL, FANTASYPROS_TARGET_LEADERS_HTML_FILE_PATH);
	}

	private boolean updateFantasyProsPPRRankingsDataSource() {
		return updater.downloadFileFromUrl(FANTASYPROS_RANKINGS_URL, FANTASYPROS_RANKINGS_HTML_FILE_PATH);
	}

	private boolean updateFantasyProsADPDataSource() {
		return updater.downloadFileFromUrl(FANTASYPROS_ADP_URL, FANTASYPROS_ADP_HTML_FILE_PATH);
	}

	private boolean updateFantasyProsNotesDataSource() {
		return updater.downloadFileFromUrl(FANTASYPROS_NOTES_URL, FANTASYPROS_NOTES_HTML_FILE_PATH);
	}

	private boolean updateFantasyProsRookiesRankingsDataSource() {
		return updater.downloadFileFromUrl(FANTASYPROS_ROOKIES_RANKINGS_URL, FANTASYPROS_ROOKIES_RANKINGS_HTML_FILE_PATH);
	}

	private boolean updateFantasyProsPositionalProjectionsDataSource() {
		boolean allPositionUpdatesSuccessful = true;
		for (Position pos : Position.values()) {
			String positionalUrl = DataSourcePaths.buildPositionalPath(pos.getAbbrev().toLowerCase(), FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_URL);
			String positionalFilePath = DataSourcePaths.buildPositionalPath(pos.getAbbrev().toLowerCase(), FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_HTML_FILE_PATH);
			allPositionUpdatesSuccessful &= updater.downloadFileFromUrl(positionalUrl, positionalFilePath);
		}
		return allPositionUpdatesSuccessful;
	}
	
}
