package com.falifa.draftbuddy.ui.model.player;

import com.falifa.draftbuddy.ui.constants.NflTeam;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.stats.RawStatsDetails;
import com.falifa.draftbuddy.ui.model.player.stats.positional.PlayerPositionalStats;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {

	private String fantasyProsId;
	private int tier;
	private String playerName;	
	private Position position;
	private String bye;
	private NflTeam team;
	
	private DraftingDetails draftingDetails;

	private RawStatsDetails priorRawStatsDetails;
	private RawStatsDetails projectedRawStatsDetails;
	
	private PlayerPositionalStats positionalStats;

	private RankMetadata rankMetadata;
	private NflTeamMetadata nflTeamMetadata;
	private OffensiveLineMetadata offensiveLineMetadata;
	private PictureMetadata pictureMetadata;
	private NotesMetadata notesMetadata;
	
	public Player() {
		this.draftingDetails = new DraftingDetails();
		this.priorRawStatsDetails = new RawStatsDetails();
		this.projectedRawStatsDetails = new RawStatsDetails();
		this.rankMetadata = new RankMetadata();
		this.nflTeamMetadata = new NflTeamMetadata();
		this.offensiveLineMetadata = new OffensiveLineMetadata();
		this.pictureMetadata = new PictureMetadata();
		this.notesMetadata = new NotesMetadata();
		this.positionalStats = new PlayerPositionalStats();
	}
	@JsonIgnore
	public String checkForHandcuff() {
		return (draftingDetails.getHandcuffs() == null) ? " - " : draftingDetails.getHandcuffs();
	}
	
	@JsonIgnore
	@Override
	public String toString() {
		return playerName;
	}
	@JsonIgnore
	public void addHandcuff(Player cuff) {
		this.draftingDetails.addBackup(cuff);
	}
	@JsonIgnore
	public String getNameAndTags() {
		return (draftingDetails.getTags() == null || draftingDetails.getTags().isEmpty()) ? playerName : playerName + " [" + draftingDetails.getTags() + "]";
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

	public RawStatsDetails getPriorRawStatsDetails() {
		return priorRawStatsDetails;
	}

	public void setPriorRawStatsDetails(RawStatsDetails priorRawStatsDetails) {
		this.priorRawStatsDetails = priorRawStatsDetails;
	}

	public RawStatsDetails getProjectedRawStatsDetails() {
		return projectedRawStatsDetails;
	}

	public void setProjectedRawStatsDetails(RawStatsDetails projectedRawStatsDetails) {
		this.projectedRawStatsDetails = projectedRawStatsDetails;
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

	public OffensiveLineMetadata getOffensiveLineMetadata() {
		return offensiveLineMetadata;
	}

	public void setOffensiveLineMetadata(OffensiveLineMetadata oLineData) {
		this.offensiveLineMetadata = oLineData;
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

	public NflTeam getTeam() {
		return team;
	}

	public void setTeam(NflTeam team) {
		this.team = team;
	}
	public PlayerPositionalStats getPositionalStats() {
		return positionalStats;
	}
	public void setPositionalStats(PlayerPositionalStats positionalStats) {
		this.positionalStats = positionalStats;
	}
	
}
