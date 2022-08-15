package com.falifa.draftbuddy.ui.model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.model.player.stats.PlayerPositionalStats;
import com.falifa.draftbuddy.ui.model.player.stats.RawStatsDetails;
import com.falifa.draftbuddy.ui.model.team.NFLTeam;
import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.FFBallersConsolidatedProjection;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.PlayerInfo;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.Projection;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {

	private String fantasyProsId;
	private int tier;
	private String playerName;	
	private Position position;
	private String bye;
	private NflTeamMetadata team;
	
	private DraftingDetails draftingDetails;

	private RawStatsDetails priorRawStatsDetails;
	private RawStatsDetails projectedRawStatsDetails;
	
	private PlayerPositionalStats positionalStats;

	private RankMetadata rankMetadata;
	private OffensiveLineMetadata offensiveLineMetadata;
	private PictureMetadata pictureMetadata;
	private NotesMetadata notesMetadata;
	
	private FFBallersConsolidatedProjection ffBallersPlayerProjection;
	private SleeperPlayerData sleeperData;
	private String sleeperId;
	

	public SleeperPlayerData getSleeperData() {
		return sleeperData;
	}
	
	public void setSleeperData(SleeperPlayerData sleeperData) {
		this.sleeperData = sleeperData;
	}
	
	public String getSleeperId() {
		return sleeperId;
	}

	public void setSleeperId(String sleeperId) {
		this.sleeperId = sleeperId;
	}

	public Player() {
		this.draftingDetails = new DraftingDetails();
		this.priorRawStatsDetails = new RawStatsDetails();
		this.projectedRawStatsDetails = new RawStatsDetails();
		this.rankMetadata = new RankMetadata();
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
	public String getPositionalSOSRank() {
//		Map<String, NFLTeam> map = NFLTeamCache.getNflTeamsByAbbreviation();
		Map<String, NFLTeam> map = NFLTeamManager.getTeamsByAbbreviation();
		NFLTeam nflTeam = map.get(team.getAbbreviation());
		try {
			switch (position) {
				case QUARTERBACK: return String.valueOf(nflTeam.getSosData().getQbRank());
				case RUNNINGBACK: return String.valueOf(nflTeam.getSosData().getRbRank());
				case WIDERECEIVER: return String.valueOf(nflTeam.getSosData().getWrRank());
				case TIGHTEND: return String.valueOf(nflTeam.getSosData().getTeRank());
				case DEFENSE: return String.valueOf(nflTeam.getSosData().getDstRank());
				case KICKER: return String.valueOf(nflTeam.getSosData().getkRank());
			}
		} catch (Exception e) {}
		return "";
	}
	@JsonIgnore
	@Override
	public String toString() {
		return playerName;
	}
	@JsonIgnore
	public String getNameAndTags() {
		return (draftingDetails.getTags() == null || draftingDetails.getTags().isEmpty()) ? playerName : playerName + " [" + draftingDetails.getTags() + "]";
	}
	
	@JsonIgnore
	public String getTagDescriptions() {
		return (draftingDetails.getTags() == null || draftingDetails.getTags().isEmpty()) ? "No tags" : buildDescriptionOfTags(draftingDetails.getTags());
	}

	public FFBallersConsolidatedProjection getFfBallersPlayerProjection() {
		return ffBallersPlayerProjection;
	}
	public void setFfBallersPlayerProjection(FFBallersConsolidatedProjection ffBallersPlayerProjection) {
		this.ffBallersPlayerProjection = ffBallersPlayerProjection;
	}

	private String buildDescriptionOfTags(String tags) {
		List<String> tagList = new ArrayList<String> ();
		for (int i = 0; i < tags.length(); i++) {
			tagList.add(String.valueOf(tags.charAt(i)));
		}
		String x = tagList.stream().map(tag -> Tag.getName(tag)).collect(Collectors.joining(", "));
		return x;
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
	
	@JsonIgnore
	public String getFirstName() {
		if (position != null && position == Position.DEFENSE && team != null) {
			return team.getCity();
		} else {
			return playerName.split(" ")[0].trim();
		}
	}
	
	@JsonIgnore
	public String getLastName() {
		if (position != null && position == Position.DEFENSE && team != null) {
			return team.getMascot();
		} else {
			return playerName.replace(getFirstName(), "").trim();
		}
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

	public NflTeamMetadata getTeam() {
		return team;
	}

	public void setTeam(NflTeamMetadata team) {
		this.team = team;
	}
	public PlayerPositionalStats getPositionalStats() {
		return positionalStats;
	}
	public void setPositionalStats(PlayerPositionalStats positionalStats) {
		this.positionalStats = positionalStats;
	}
	
	@JsonIgnore
	public String getSosBadgeClass(String text) {
		if (text.equals("")) { return "even"; }
		int value = Integer.valueOf(text);
		if (value > 27) { return "neg-40";
		} else if (value > 24) { return "neg-20";
		} else if (value > 21) { return "neg-10";
		} else if (value > 18) { return "neg-5";
		} else if (value > 15) { return "neg-2";
		} else if (value > 12) { return "pos-2";
		} else if (value > 9) { return "pos-5";
		} else if (value > 6) { return "pos-10";
		} else if (value > 3) { return "pos-20";
		} else { return "pos-40";
		}
	}
	
}
