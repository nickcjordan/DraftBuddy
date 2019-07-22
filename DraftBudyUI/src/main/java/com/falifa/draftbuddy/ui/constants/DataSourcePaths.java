package com.falifa.draftbuddy.ui.constants;

public class DataSourcePaths {
	
	public static final String FANTASYPROS_BASE_URL = "https://www.fantasypros.com/nfl";
	public static final String FANTASYPROS_BASE_HTML_FILE_PATH = "src/main/resources/data/html/fantasyPros/";
	
	

	public static final String FANTASYPROS_RANKINGS_URL = FANTASYPROS_BASE_URL + "/rankings/ppr-cheatsheets.php";
	public static final String FANTASYPROS_RANKINGS_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH + "fantasy_pros_ppr_rankings.html";
	
	public static final String FANTASYPROS_NOTES_URL = FANTASYPROS_BASE_URL + "/notes/draft-overall.php?type=PPR";
	public static final String FANTASYPROS_NOTES_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH + "fantasy_pros_notes.html";
	
	public static final String FANTASYPROS_ADP_URL = FANTASYPROS_BASE_URL + "/adp/ppr-overall.php";
	public static final String FANTASYPROS_ADP_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH + "fantasy_pros_adp.html";
	
	public static final String FANTASYPROS_ROOKIES_RANKINGS_URL = FANTASYPROS_BASE_URL + "/rankings/rookies.php";
	public static final String FANTASYPROS_ROOKIES_RANKINGS_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH + "fantasy_pros_rookies_rankings.html";
	
	public static final String FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_URL = FANTASYPROS_BASE_URL + "/projections/%s.php?week=draft";
	public static final String FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH + "fantasy_pros_positional_projections_%s.html";
	
	public static String buildPositionalPath(String position, String path) {
		String pos = (position.equalsIgnoreCase("def")) ? "dst" : position.toLowerCase();
		return String.format(path, pos);
	}
	
	public static final String MASTER_PLAYERS_JSON_FILE_PATH = "src/main/resources/data/master/players.json";
	
//	public static final String FANTASYPROS__URL = FANTASYPROS_BASE_URL + "/rankings/ppr-cheatsheets.php";
//	public static final String FANTASYPROS__HTML_FILE_PATH = "src/main/resources/data/html/ppr_rankings.html";
//	
//	public static final String FANTASYPROS__URL = FANTASYPROS_BASE_URL + "/rankings/ppr-cheatsheets.php";
//	public static final String FANTASYPROS__HTML_FILE_PATH = "src/main/resources/data/html/ppr_rankings.html";
	

	
	public static final String DRAFT_LOGIC_PROPERTIES_PATH = "src/main/resources/data/logic.properties";
	public static final String PLAYERNOTES_CUSTOM_PATH = "src/main/resources/data/nicks_notes.csv";
	public static final String PLAYERNOTES_EXPERTS_PATH = "src/main/resources/data/FantasyPros_notes.csv";
	public static final String DRAFTSTRATEGY_CUSTOM_PATH = "src/main/resources/data/draftStrategyByRound.csv";
	public static final String OLINERANK_TO_PLAYER_MAPPING_PATH = "src/main/resources/data/o-line_to_player_rankings.csv";
	public static final String PLAYER_PICTURES_PATH = "src/main/resources/data/playerPics.csv";
	public static final String PLAYERS_TO_TARGET_PATH = "src/main/resources/data/playersToTarget.csv";
	public static final String TAGS_CUSTOM_PATH = "src/main/resources/data/tags.csv";
	public static final String PREVIOUS_SEASON_TARGET_SHARE_PATH = "src/main/resources/data/targets.csv";
	public static final String NFL_TEAM_NAMES_PATH = "src/main/resources/data/team_names.csv";
	
	public static final String ECR_FANTASYPROS_TEST_PATH = "src/main/resources/data/master_players_test.csv";
	public static final String CHROMEDRIVER_PATH = "src/main/resources/driver/75/chromedriver.exe";
	
	
	
	public static final String PLAYER_NOTES_HTML_PATH = "src/main/resources/data/html/playerNotesHtml.html";
	public static final String PPR_RANKINGS_HTML_PATH = "src/main/resources/data/html/master_players.html";
	
	
	public static final String QB_PROJECTIONS_PATH = "src/main/resources/data/projections/FantasyPros_Fantasy_Football_Projections_QB.csv";
	public static final String RB_PROJECTIONS_PATH = "src/main/resources/data/projections/FantasyPros_Fantasy_Football_Projections_RB.csv";
	public static final String WR_PROJECTIONS_PATH = "src/main/resources/data/projections/FantasyPros_Fantasy_Football_Projections_WR.csv";
	public static final String TE_PROJECTIONS_PATH = "src/main/resources/data/projections/FantasyPros_Fantasy_Football_Projections_TE.csv";
	public static final String K_PROJECTIONS_PATH = "src/main/resources/data/projections/FantasyPros_Fantasy_Football_Projections_K.csv";
	public static final String DST_PROJECTIONS_PATH = "src/main/resources/data/projections/FantasyPros_Fantasy_Football_Projections_DST.csv";
	

}
