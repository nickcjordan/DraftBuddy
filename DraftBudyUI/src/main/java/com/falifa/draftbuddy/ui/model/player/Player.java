package com.falifa.draftbuddy.ui.model.player;

import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.ADP;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.AVG;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.BEST;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.BYE;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.PLAYER_NAME;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.RANK;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.STD_DEV;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.TEAM_NAME;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.VERSUS_ADP;
import static com.falifa.draftbuddy.ui.constants.CSVFieldMapping.WORST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.falifa.draftbuddy.ui.Log;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.controller.BaseController;

public class Player {

	String id;
	String fantasyProsId;
	int tier;
	private String playerName;	
	private String teamName;	
	private String pos;				
	private Position position;
	private String bye;
	
	private DraftingDetails draftingDetails;

	private PlayerStatistics priorStats;
	private PlayerStatistics projectedStats;

	private RankMetadata rankMetadata;
	private NflTeamMetadata nflTeamMetadata; // TODO add to this
	private OffensiveLineMetadata oLineData;
	private PictureMetadata pictureMetadata;
	private NotesMetadata notesMetadata; //TODO add to this
	
	public Player() {
		this.draftingDetails = new DraftingDetails();
		this.priorStats = new PlayerStatistics();
		this.projectedStats = new PlayerStatistics();
		this.rankMetadata = new RankMetadata();
		this.nflTeamMetadata = new NflTeamMetadata();
		this.oLineData = new OffensiveLineMetadata();
		this.pictureMetadata = new PictureMetadata();
		this.notesMetadata = new NotesMetadata();
	}
	
	
	public String checkForHandcuff() {
		return (draftingDetails.getHandcuffs() == null) ? " - " : draftingDetails.getHandcuffs();
	}
	
	public String getShort() {
		return "(" + id + ") " + getNameAndTags(); 
	}
	
	public String getNameAndId() {
		return "(" + id + ") " + getPlayerName(); 
	}
	
	@Override
	public String toString() {
		return playerName;
	}

	public void addHandcuff(Player cuff) {
		this.draftingDetails.addBackup(cuff);
	}

	public String stats(){
		return playerName + "   (" + this.rankMetadata.getPositionRank() + "/" + this.rankMetadata.getOverallRank() + ") [" + pos + ", " + teamName + ", bye:" + bye + "]";
	}
	
	public String getNameAndTags() {
		return (draftingDetails.getTags() == null || draftingDetails.getTags().isEmpty()) ? playerName : playerName + " [" + draftingDetails.getTags() + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFantasyProsId() {
		return fantasyProsId;
	}

	public void setFantasyProsId(String fantasyProsId) {
		this.fantasyProsId = fantasyProsId;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getBye() {
		return bye;
	}

	public void setBye(String bye) {
		this.bye = bye;
	}

	public PlayerStatistics getPriorStats() {
		return priorStats;
	}

	public void setPriorStats(PlayerStatistics priorStats) {
		this.priorStats = priorStats;
	}

	public PlayerStatistics getProjectedStats() {
		return projectedStats;
	}

	public void setProjectedStats(PlayerStatistics projectedStats) {
		this.projectedStats = projectedStats;
	}

	public DraftingDetails getDraftingDetails() {
		return draftingDetails;
	}

	public void setDraftingDetails(DraftingDetails draftMetadata) {
		this.draftingDetails = draftMetadata;
	}

	public RankMetadata getRankMetadata() {
		return rankMetadata;
	}

	public void setRankMetadata(RankMetadata rankMetadata) {
		this.rankMetadata = rankMetadata;
	}

	public NflTeamMetadata getNflTeamMetadata() {
		return nflTeamMetadata;
	}

	public void setNflTeamMetadata(NflTeamMetadata nflTeamMetadata) {
		this.nflTeamMetadata = nflTeamMetadata;
	}

	public OffensiveLineMetadata getoLineData() {
		return oLineData;
	}

	public void setoLineData(OffensiveLineMetadata oLineData) {
		this.oLineData = oLineData;
	}

	public PictureMetadata getPictureMetadata() {
		return pictureMetadata;
	}

	public void setPictureMetadata(PictureMetadata pictureMetadata) {
		this.pictureMetadata = pictureMetadata;
	}

	public NotesMetadata getNotesMetadata() {
		return notesMetadata;
	}

	public void setNotesMetadata(NotesMetadata notesMetadata) {
		this.notesMetadata = notesMetadata;
	}
	
}
