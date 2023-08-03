package com.falifa.draftbuddy.ui.constants;

public class DataSourcePaths {

	public static final String PLAYER_IMAGES_FILE_PATH = "/images/players/";
	public static final String WEB_RESOURCES_BASE_FILE_PATH = "src/main/webapp";
	public static final String PLAYER_IMAGES_BASE_FILE_PATH = WEB_RESOURCES_BASE_FILE_PATH + PLAYER_IMAGES_FILE_PATH;

	public static final String PLAYER_TAGS_FILE_PATH = "src/main/resources/data/tags.csv";
	public static final String DRAFT_STRATEGY_BY_ROUND_FILE_PATH = "src/main/resources/data/strategyByRound.csv";

	// *************** FANTASY PROS ***************
	public static final String FANTASYPROS_BASE_URL = "https://www.fantasypros.com/nfl";
	public static final String FANTASYPROS_BASE_HTML_FILE_PATH = "src/main/resources/data/html/fantasyPros/";

	public static final String FANTASYPROS_RANKINGS_URL = FANTASYPROS_BASE_URL + "/rankings/ppr-cheatsheets.php";
	public static final String FANTASYPROS_RANKINGS_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH
			+ "fantasy_pros_ppr_rankings.html";

	public static final String FANTASYPROS_NOTES_URL = FANTASYPROS_BASE_URL + "/notes/draft-overall.php?type=PPR";
	public static final String FANTASYPROS_NOTES_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH
			+ "fantasy_pros_notes.html";

	public static final String FANTASYPROS_ADP_URL = FANTASYPROS_BASE_URL + "/adp/ppr-overall.php";
	public static final String FANTASYPROS_ADP_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH
			+ "fantasy_pros_adp.html";

	public static final String FANTASYPROS_ROOKIES_RANKINGS_URL = FANTASYPROS_BASE_URL + "/rankings/rookies.php";
	public static final String FANTASYPROS_ROOKIES_RANKINGS_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH
			+ "fantasy_pros_rookies_rankings.html";

	public static final String FANTASYPROS_TARGET_LEADERS_URL = FANTASYPROS_BASE_URL + "/reports/targets/";
	public static final String FANTASYPROS_TARGET_LEADERS_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH
			+ "fantasy_pros_target_leaders.html";
	
	public static final String FANTASYPROS_TEAM_TARGETS_URL = FANTASYPROS_BASE_URL + "/reports/targets-distribution/";
	public static final String FANTASYPROS_TEAM_TARGETS_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH
			+ "fantasy_pros_team_targets.html";

	public static final String FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_URL = FANTASYPROS_BASE_URL
			+ "/projections/%s.php?scoring=PPR&week=draft";
	public static final String FANTASYPROS_POSITIONAL_PROJECTIONS_BASE_HTML_FILE_PATH = FANTASYPROS_BASE_HTML_FILE_PATH
			+ "fantasy_pros_positional_projections_%s.html";

	// *************** FANTASY FOOTBALLERS ***************
	public static final String FANTASYFOOTBALLERS_BASE_URL = "https://www.thefantasyfootballers.com/";
	public static final String FANTASYFOOTBALLERS_VALUES_URL = FANTASYFOOTBALLERS_BASE_URL
			+ "/udk-expert-lists-values/";

	public static final String FANTASYFOOTBALLERS_QB_RANKINGS_URL = FANTASYFOOTBALLERS_BASE_URL
			+ "2021-quarterback-rankings/?scoring=4pt";
	public static final String FANTASYFOOTBALLERS_RB_RANKINGS_URL = FANTASYFOOTBALLERS_BASE_URL
			+ "2021-running-back-rankings/?scoring=ppr";
	public static final String FANTASYFOOTBALLERS_WR_RANKINGS_URL = FANTASYFOOTBALLERS_BASE_URL
			+ "2021-wide-receiver-rankings/?scoring=ppr";
	public static final String FANTASYFOOTBALLERS_TE_RANKINGS_URL = FANTASYFOOTBALLERS_BASE_URL
			+ "2021-tight-end-rankings/?scoring=ppr";
	public static final String FANTASYFOOTBALLERS_DST_RANKINGS_URL = FANTASYFOOTBALLERS_BASE_URL
			+ "2021-defense-rankings/";
	public static final String FANTASYFOOTBALLERS_K_RANKINGS_URL = FANTASYFOOTBALLERS_BASE_URL
			+ "2021-kicker-rankings/";

	public static final String FANTASYFOOTBALLERS_BASE_FILE_PATH = "src/main/resources/data/html/fantasyfootballers/";
	public static final String FANTASYFOOTBALLERS_BASE_RANKINGS_PATH = FANTASYFOOTBALLERS_BASE_FILE_PATH + "rankings/";

	public static final String FANTASYFOOTBALLERS_QB_RANKINGS_PATH = FANTASYFOOTBALLERS_BASE_RANKINGS_PATH
			+ "ffballers_rankings_qb.html";
	public static final String FANTASYFOOTBALLERS_RB_RANKINGS_PATH = FANTASYFOOTBALLERS_BASE_RANKINGS_PATH
			+ "ffballers_rankings_rb.html";
	public static final String FANTASYFOOTBALLERS_WR_RANKINGS_PATH = FANTASYFOOTBALLERS_BASE_RANKINGS_PATH
			+ "ffballers_rankings_wr.html";
	public static final String FANTASYFOOTBALLERS_TE_RANKINGS_PATH = FANTASYFOOTBALLERS_BASE_RANKINGS_PATH
			+ "ffballers_rankings_te.html";
	public static final String FANTASYFOOTBALLERS_DST_RANKINGS_PATH = FANTASYFOOTBALLERS_BASE_RANKINGS_PATH
			+ "ffballers_rankings_dst.html";
	public static final String FANTASYFOOTBALLERS_K_RANKINGS_PATH = FANTASYFOOTBALLERS_BASE_RANKINGS_PATH
			+ "ffballers_rankings_k.html";

	public static final String FANTASYFOOTBALLERS_VALUES_PATH = FANTASYFOOTBALLERS_BASE_FILE_PATH
			+ "UDK - Expert Lists - Values - Fantasy Footballers Podcast.html";
	public static final String FANTASYFOOTBALLERS_BREAKOUTS_PATH = FANTASYFOOTBALLERS_BASE_FILE_PATH
			+ "UDK - Expert Lists - Breakouts - Fantasy Footballers Podcast.html";
	public static final String FANTASYFOOTBALLERS_BUSTS_PATH = FANTASYFOOTBALLERS_BASE_FILE_PATH
			+ "UDK - Expert Lists - Busts - Fantasy Footballers Podcast.html";
	public static final String FANTASYFOOTBALLERS_SLEEPERS_PATH = FANTASYFOOTBALLERS_BASE_FILE_PATH
			+ "UDK - Expert Lists - Sleepers - Fantasy Footballers Podcast.html";
	public static final String FANTASYFOOTBALLERS_INJURIES_PATH = FANTASYFOOTBALLERS_BASE_FILE_PATH
			+ "UDK - Injury Report - Fantasy Footballers Podcast.html";
	public static final String FANTASYFOOTBALLERS_ROOKIES_PATH = FANTASYFOOTBALLERS_BASE_FILE_PATH
			+ "UDK - Rookie Report - Fantasy Footballers Podcast.html";

	public static String buildPositionalPath(String position, String path) {
		String pos = (position.equalsIgnoreCase("def")) ? "dst" : position.toLowerCase();
		return String.format(path, pos);
	}

	public static final String MASTER_PLAYERS_JSON_FILE_PATH = "src/main/resources/data/master/players.json";
	public static final String MASTER_NFL_TEAMS_JSON_FILE_PATH = "src/main/resources/data/master/nfl.json";
	public static final String MASTER_SLEEPER_PLAYERS_JSON_FILE_PATH = "src/main/resources/data/master/sleeper_players.json";

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

	
	public static final String LATE_ROUND_GUIDE_PATH = "src/main/resources/data/LateRoundDraftGuideRankings.csv";

}
