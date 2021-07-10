
package com.falifa.draftbuddy.ui.prep.scraper.webjson.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "player_id",
    "player_name",
    "sportsdata_id",
    "player_team_id",
    "player_position_id",
    "player_positions",
    "player_short_name",
    "player_eligibility",
    "player_yahoo_positions",
    "player_page_url",
    "player_filename",
    "player_square_image_url",
    "player_image_url",
    "player_yahoo_id",
    "cbs_player_id",
    "player_bye_week",
    "player_owned_avg",
    "player_owned_espn",
    "player_owned_yahoo",
    "player_ecr_delta",
    "rank_ecr",
    "rank_min",
    "rank_max",
    "rank_ave",
    "rank_std",
    "pos_rank",
    "tier"
})
@Generated("jsonschema2pojo")
public class Player {

    @JsonProperty("player_id")
    private Integer playerId;
    @JsonProperty("player_name")
    private String playerName;
    @JsonProperty("sportsdata_id")
    private String sportsdataId;
    @JsonProperty("player_team_id")
    private String playerTeamId;
    @JsonProperty("player_position_id")
    private String playerPositionId;
    @JsonProperty("player_positions")
    private String playerPositions;
    @JsonProperty("player_short_name")
    private String playerShortName;
    @JsonProperty("player_eligibility")
    private String playerEligibility;
    @JsonProperty("player_yahoo_positions")
    private String playerYahooPositions;
    @JsonProperty("player_page_url")
    private String playerPageUrl;
    @JsonProperty("player_filename")
    private String playerFilename;
    @JsonProperty("player_square_image_url")
    private String playerSquareImageUrl;
    @JsonProperty("player_image_url")
    private String playerImageUrl;
    @JsonProperty("player_yahoo_id")
    private String playerYahooId;
    @JsonProperty("cbs_player_id")
    private String cbsPlayerId;
    @JsonProperty("player_bye_week")
    private String playerByeWeek;
    @JsonProperty("player_owned_avg")
    private Integer playerOwnedAvg;
    @JsonProperty("player_owned_espn")
    private Double playerOwnedEspn;
    @JsonProperty("player_owned_yahoo")
    private Integer playerOwnedYahoo;
    @JsonProperty("player_ecr_delta")
    private Object playerEcrDelta;
    @JsonProperty("rank_ecr")
    private Integer rankEcr;
    @JsonProperty("rank_min")
    private String rankMin;
    @JsonProperty("rank_max")
    private String rankMax;
    @JsonProperty("rank_ave")
    private String rankAve;
    @JsonProperty("rank_std")
    private String rankStd;
    @JsonProperty("pos_rank")
    private String posRank;
    @JsonProperty("tier")
    private Integer tier;

    @JsonProperty("player_id")
    public Integer getPlayerId() {
        return playerId;
    }

    @JsonProperty("player_id")
    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    @JsonProperty("player_name")
    public String getPlayerName() {
        return playerName;
    }

    @JsonProperty("player_name")
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @JsonProperty("sportsdata_id")
    public String getSportsdataId() {
        return sportsdataId;
    }

    @JsonProperty("sportsdata_id")
    public void setSportsdataId(String sportsdataId) {
        this.sportsdataId = sportsdataId;
    }

    @JsonProperty("player_team_id")
    public String getPlayerTeamId() {
        return playerTeamId;
    }

    @JsonProperty("player_team_id")
    public void setPlayerTeamId(String playerTeamId) {
        this.playerTeamId = playerTeamId;
    }

    @JsonProperty("player_position_id")
    public String getPlayerPositionId() {
        return playerPositionId;
    }

    @JsonProperty("player_position_id")
    public void setPlayerPositionId(String playerPositionId) {
        this.playerPositionId = playerPositionId;
    }

    @JsonProperty("player_positions")
    public String getPlayerPositions() {
        return playerPositions;
    }

    @JsonProperty("player_positions")
    public void setPlayerPositions(String playerPositions) {
        this.playerPositions = playerPositions;
    }

    @JsonProperty("player_short_name")
    public String getPlayerShortName() {
        return playerShortName;
    }

    @JsonProperty("player_short_name")
    public void setPlayerShortName(String playerShortName) {
        this.playerShortName = playerShortName;
    }

    @JsonProperty("player_eligibility")
    public String getPlayerEligibility() {
        return playerEligibility;
    }

    @JsonProperty("player_eligibility")
    public void setPlayerEligibility(String playerEligibility) {
        this.playerEligibility = playerEligibility;
    }

    @JsonProperty("player_yahoo_positions")
    public String getPlayerYahooPositions() {
        return playerYahooPositions;
    }

    @JsonProperty("player_yahoo_positions")
    public void setPlayerYahooPositions(String playerYahooPositions) {
        this.playerYahooPositions = playerYahooPositions;
    }

    @JsonProperty("player_page_url")
    public String getPlayerPageUrl() {
        return playerPageUrl;
    }

    @JsonProperty("player_page_url")
    public void setPlayerPageUrl(String playerPageUrl) {
        this.playerPageUrl = playerPageUrl;
    }

    @JsonProperty("player_filename")
    public String getPlayerFilename() {
        return playerFilename;
    }

    @JsonProperty("player_filename")
    public void setPlayerFilename(String playerFilename) {
        this.playerFilename = playerFilename;
    }

    @JsonProperty("player_square_image_url")
    public String getPlayerSquareImageUrl() {
        return playerSquareImageUrl;
    }

    @JsonProperty("player_square_image_url")
    public void setPlayerSquareImageUrl(String playerSquareImageUrl) {
        this.playerSquareImageUrl = playerSquareImageUrl;
    }

    @JsonProperty("player_image_url")
    public String getPlayerImageUrl() {
        return playerImageUrl;
    }

    @JsonProperty("player_image_url")
    public void setPlayerImageUrl(String playerImageUrl) {
        this.playerImageUrl = playerImageUrl;
    }

    @JsonProperty("player_yahoo_id")
    public String getPlayerYahooId() {
        return playerYahooId;
    }

    @JsonProperty("player_yahoo_id")
    public void setPlayerYahooId(String playerYahooId) {
        this.playerYahooId = playerYahooId;
    }

    @JsonProperty("cbs_player_id")
    public String getCbsPlayerId() {
        return cbsPlayerId;
    }

    @JsonProperty("cbs_player_id")
    public void setCbsPlayerId(String cbsPlayerId) {
        this.cbsPlayerId = cbsPlayerId;
    }

    @JsonProperty("player_bye_week")
    public String getPlayerByeWeek() {
        return playerByeWeek;
    }

    @JsonProperty("player_bye_week")
    public void setPlayerByeWeek(String playerByeWeek) {
        this.playerByeWeek = playerByeWeek;
    }

    @JsonProperty("player_owned_avg")
    public Integer getPlayerOwnedAvg() {
        return playerOwnedAvg;
    }

    @JsonProperty("player_owned_avg")
    public void setPlayerOwnedAvg(Integer playerOwnedAvg) {
        this.playerOwnedAvg = playerOwnedAvg;
    }

    @JsonProperty("player_owned_espn")
    public Double getPlayerOwnedEspn() {
        return playerOwnedEspn;
    }

    @JsonProperty("player_owned_espn")
    public void setPlayerOwnedEspn(Double playerOwnedEspn) {
        this.playerOwnedEspn = playerOwnedEspn;
    }

    @JsonProperty("player_owned_yahoo")
    public Integer getPlayerOwnedYahoo() {
        return playerOwnedYahoo;
    }

    @JsonProperty("player_owned_yahoo")
    public void setPlayerOwnedYahoo(Integer playerOwnedYahoo) {
        this.playerOwnedYahoo = playerOwnedYahoo;
    }

    @JsonProperty("player_ecr_delta")
    public Object getPlayerEcrDelta() {
        return playerEcrDelta;
    }

    @JsonProperty("player_ecr_delta")
    public void setPlayerEcrDelta(Object playerEcrDelta) {
        this.playerEcrDelta = playerEcrDelta;
    }

    @JsonProperty("rank_ecr")
    public Integer getRankEcr() {
        return rankEcr;
    }

    @JsonProperty("rank_ecr")
    public void setRankEcr(Integer rankEcr) {
        this.rankEcr = rankEcr;
    }

    @JsonProperty("rank_min")
    public String getRankMin() {
        return rankMin;
    }

    @JsonProperty("rank_min")
    public void setRankMin(String rankMin) {
        this.rankMin = rankMin;
    }

    @JsonProperty("rank_max")
    public String getRankMax() {
        return rankMax;
    }

    @JsonProperty("rank_max")
    public void setRankMax(String rankMax) {
        this.rankMax = rankMax;
    }

    @JsonProperty("rank_ave")
    public String getRankAve() {
        return rankAve;
    }

    @JsonProperty("rank_ave")
    public void setRankAve(String rankAve) {
        this.rankAve = rankAve;
    }

    @JsonProperty("rank_std")
    public String getRankStd() {
        return rankStd;
    }

    @JsonProperty("rank_std")
    public void setRankStd(String rankStd) {
        this.rankStd = rankStd;
    }

    @JsonProperty("pos_rank")
    public String getPosRank() {
        return posRank;
    }

    @JsonProperty("pos_rank")
    public void setPosRank(String posRank) {
        this.posRank = posRank;
    }

    @JsonProperty("tier")
    public Integer getTier() {
        return tier;
    }

    @JsonProperty("tier")
    public void setTier(Integer tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Player.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("playerId");
        sb.append('=');
        sb.append(((this.playerId == null)?"<null>":this.playerId));
        sb.append(',');
        sb.append("playerName");
        sb.append('=');
        sb.append(((this.playerName == null)?"<null>":this.playerName));
        sb.append(',');
        sb.append("sportsdataId");
        sb.append('=');
        sb.append(((this.sportsdataId == null)?"<null>":this.sportsdataId));
        sb.append(',');
        sb.append("playerTeamId");
        sb.append('=');
        sb.append(((this.playerTeamId == null)?"<null>":this.playerTeamId));
        sb.append(',');
        sb.append("playerPositionId");
        sb.append('=');
        sb.append(((this.playerPositionId == null)?"<null>":this.playerPositionId));
        sb.append(',');
        sb.append("playerPositions");
        sb.append('=');
        sb.append(((this.playerPositions == null)?"<null>":this.playerPositions));
        sb.append(',');
        sb.append("playerShortName");
        sb.append('=');
        sb.append(((this.playerShortName == null)?"<null>":this.playerShortName));
        sb.append(',');
        sb.append("playerEligibility");
        sb.append('=');
        sb.append(((this.playerEligibility == null)?"<null>":this.playerEligibility));
        sb.append(',');
        sb.append("playerYahooPositions");
        sb.append('=');
        sb.append(((this.playerYahooPositions == null)?"<null>":this.playerYahooPositions));
        sb.append(',');
        sb.append("playerPageUrl");
        sb.append('=');
        sb.append(((this.playerPageUrl == null)?"<null>":this.playerPageUrl));
        sb.append(',');
        sb.append("playerFilename");
        sb.append('=');
        sb.append(((this.playerFilename == null)?"<null>":this.playerFilename));
        sb.append(',');
        sb.append("playerSquareImageUrl");
        sb.append('=');
        sb.append(((this.playerSquareImageUrl == null)?"<null>":this.playerSquareImageUrl));
        sb.append(',');
        sb.append("playerImageUrl");
        sb.append('=');
        sb.append(((this.playerImageUrl == null)?"<null>":this.playerImageUrl));
        sb.append(',');
        sb.append("playerYahooId");
        sb.append('=');
        sb.append(((this.playerYahooId == null)?"<null>":this.playerYahooId));
        sb.append(',');
        sb.append("cbsPlayerId");
        sb.append('=');
        sb.append(((this.cbsPlayerId == null)?"<null>":this.cbsPlayerId));
        sb.append(',');
        sb.append("playerByeWeek");
        sb.append('=');
        sb.append(((this.playerByeWeek == null)?"<null>":this.playerByeWeek));
        sb.append(',');
        sb.append("playerOwnedAvg");
        sb.append('=');
        sb.append(((this.playerOwnedAvg == null)?"<null>":this.playerOwnedAvg));
        sb.append(',');
        sb.append("playerOwnedEspn");
        sb.append('=');
        sb.append(((this.playerOwnedEspn == null)?"<null>":this.playerOwnedEspn));
        sb.append(',');
        sb.append("playerOwnedYahoo");
        sb.append('=');
        sb.append(((this.playerOwnedYahoo == null)?"<null>":this.playerOwnedYahoo));
        sb.append(',');
        sb.append("playerEcrDelta");
        sb.append('=');
        sb.append(((this.playerEcrDelta == null)?"<null>":this.playerEcrDelta));
        sb.append(',');
        sb.append("rankEcr");
        sb.append('=');
        sb.append(((this.rankEcr == null)?"<null>":this.rankEcr));
        sb.append(',');
        sb.append("rankMin");
        sb.append('=');
        sb.append(((this.rankMin == null)?"<null>":this.rankMin));
        sb.append(',');
        sb.append("rankMax");
        sb.append('=');
        sb.append(((this.rankMax == null)?"<null>":this.rankMax));
        sb.append(',');
        sb.append("rankAve");
        sb.append('=');
        sb.append(((this.rankAve == null)?"<null>":this.rankAve));
        sb.append(',');
        sb.append("rankStd");
        sb.append('=');
        sb.append(((this.rankStd == null)?"<null>":this.rankStd));
        sb.append(',');
        sb.append("posRank");
        sb.append('=');
        sb.append(((this.posRank == null)?"<null>":this.posRank));
        sb.append(',');
        sb.append("tier");
        sb.append('=');
        sb.append(((this.tier == null)?"<null>":this.tier));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
