
package com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "player_id",
    "analyst_id",
    "season_type",
    "season",
    "passing_attempts",
    "passing_yards",
    "passing_touchdowns",
    "passing_completions",
    "rushing_attempts",
    "rushing_yards",
    "rushing_yards_per_attempt",
    "rushing_touchdowns",
    "receptions",
    "receiving_yards",
    "receiving_yards_per_reception",
    "receiving_touchdowns",
    "interceptions_thrown",
    "fumbles_lost",
    "risk",
    "created_at",
    "updated_at",
    "receiving_targets",
    "analyst_name",
    "name",
    "fantasy_position",
    "team",
    "number",
    "bye_week",
    "udk",
    "slug",
    "adp",
    "adp_ppr",
    "adp_half_ppr",
    "headshot",
    "tagged"
})
@Generated("jsonschema2pojo")
public class Projection {

    @JsonProperty("player_id")
    private String playerId;
    @JsonProperty("analyst_id")
    private String analystId;
    @JsonProperty("season_type")
    private String seasonType;
    @JsonProperty("season")
    private String season;
    @JsonProperty("passing_attempts")
    private Integer passingAttempts;
    @JsonProperty("passing_yards")
    private Integer passingYards;
    @JsonProperty("passing_touchdowns")
    private Integer passingTouchdowns;
    @JsonProperty("passing_completions")
    private Integer passingCompletions;
    @JsonProperty("rushing_attempts")
    private Integer rushingAttempts;
    @JsonProperty("rushing_yards")
    private Integer rushingYards;
    @JsonProperty("rushing_yards_per_attempt")
    private Double rushingYardsPerAttempt;
    @JsonProperty("rushing_touchdowns")
    private Integer rushingTouchdowns;
    @JsonProperty("receptions")
    private Integer receptions;
    @JsonProperty("receiving_yards")
    private Integer receivingYards;
    @JsonProperty("receiving_yards_per_reception")
    private Integer receivingYardsPerReception;
    @JsonProperty("receiving_touchdowns")
    private Integer receivingTouchdowns;
    @JsonProperty("interceptions_thrown")
    private Integer interceptionsThrown;
    @JsonProperty("fumbles_lost")
    private Integer fumblesLost;
    @JsonProperty("risk")
    private Integer risk;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("receiving_targets")
    private String receivingTargets;
    @JsonProperty("analyst_name")
    private String analystName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("fantasy_position")
    private String fantasyPosition;
    @JsonProperty("team")
    private String team;
    @JsonProperty("number")
    private String number;
    @JsonProperty("bye_week")
    private String byeWeek;
    @JsonProperty("udk")
    private String udk;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("adp")
    private String adp;
    @JsonProperty("adp_ppr")
    private String adpPpr;
    @JsonProperty("adp_half_ppr")
    private String adpHalfPpr;
    @JsonProperty("headshot")
    private String headshot;
    @JsonProperty("tagged")
    private Boolean tagged;

    @JsonProperty("player_id")
    public String getPlayerId() {
        return playerId;
    }

    @JsonProperty("player_id")
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @JsonProperty("analyst_id")
    public String getAnalystId() {
        return analystId;
    }

    @JsonProperty("analyst_id")
    public void setAnalystId(String analystId) {
        this.analystId = analystId;
    }

    @JsonProperty("season_type")
    public String getSeasonType() {
        return seasonType;
    }

    @JsonProperty("season_type")
    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }

    @JsonProperty("season")
    public String getSeason() {
        return season;
    }

    @JsonProperty("season")
    public void setSeason(String season) {
        this.season = season;
    }

    @JsonProperty("passing_attempts")
    public Integer getPassingAttempts() {
        return passingAttempts;
    }

    @JsonProperty("passing_attempts")
    public void setPassingAttempts(Integer passingAttempts) {
        this.passingAttempts = passingAttempts;
    }

    @JsonProperty("passing_yards")
    public Integer getPassingYards() {
        return passingYards;
    }

    @JsonProperty("passing_yards")
    public void setPassingYards(Integer passingYards) {
        this.passingYards = passingYards;
    }

    @JsonProperty("passing_touchdowns")
    public Integer getPassingTouchdowns() {
        return passingTouchdowns;
    }

    @JsonProperty("passing_touchdowns")
    public void setPassingTouchdowns(Integer passingTouchdowns) {
        this.passingTouchdowns = passingTouchdowns;
    }

    @JsonProperty("passing_completions")
    public Integer getPassingCompletions() {
        return passingCompletions;
    }

    @JsonProperty("passing_completions")
    public void setPassingCompletions(Integer passingCompletions) {
        this.passingCompletions = passingCompletions;
    }

    @JsonProperty("rushing_attempts")
    public Integer getRushingAttempts() {
        return rushingAttempts;
    }

    @JsonProperty("rushing_attempts")
    public void setRushingAttempts(Integer rushingAttempts) {
        this.rushingAttempts = rushingAttempts;
    }

    @JsonProperty("rushing_yards")
    public Integer getRushingYards() {
        return rushingYards;
    }

    @JsonProperty("rushing_yards")
    public void setRushingYards(Integer rushingYards) {
        this.rushingYards = rushingYards;
    }

    @JsonProperty("rushing_yards_per_attempt")
    public Double getRushingYardsPerAttempt() {
        return rushingYardsPerAttempt;
    }

    @JsonProperty("rushing_yards_per_attempt")
    public void setRushingYardsPerAttempt(Double rushingYardsPerAttempt) {
        this.rushingYardsPerAttempt = rushingYardsPerAttempt;
    }

    @JsonProperty("rushing_touchdowns")
    public Integer getRushingTouchdowns() {
        return rushingTouchdowns;
    }

    @JsonProperty("rushing_touchdowns")
    public void setRushingTouchdowns(Integer rushingTouchdowns) {
        this.rushingTouchdowns = rushingTouchdowns;
    }

    @JsonProperty("receptions")
    public Integer getReceptions() {
        return receptions;
    }

    @JsonProperty("receptions")
    public void setReceptions(Integer receptions) {
        this.receptions = receptions;
    }

    @JsonProperty("receiving_yards")
    public Integer getReceivingYards() {
        return receivingYards;
    }

    @JsonProperty("receiving_yards")
    public void setReceivingYards(Integer receivingYards) {
        this.receivingYards = receivingYards;
    }

    @JsonProperty("receiving_yards_per_reception")
    public Integer getReceivingYardsPerReception() {
        return receivingYardsPerReception;
    }

    @JsonProperty("receiving_yards_per_reception")
    public void setReceivingYardsPerReception(Integer receivingYardsPerReception) {
        this.receivingYardsPerReception = receivingYardsPerReception;
    }

    @JsonProperty("receiving_touchdowns")
    public Integer getReceivingTouchdowns() {
        return receivingTouchdowns;
    }

    @JsonProperty("receiving_touchdowns")
    public void setReceivingTouchdowns(Integer receivingTouchdowns) {
        this.receivingTouchdowns = receivingTouchdowns;
    }

    @JsonProperty("interceptions_thrown")
    public Integer getInterceptionsThrown() {
        return interceptionsThrown;
    }

    @JsonProperty("interceptions_thrown")
    public void setInterceptionsThrown(Integer interceptionsThrown) {
        this.interceptionsThrown = interceptionsThrown;
    }

    @JsonProperty("fumbles_lost")
    public Integer getFumblesLost() {
        return fumblesLost;
    }

    @JsonProperty("fumbles_lost")
    public void setFumblesLost(Integer fumblesLost) {
        this.fumblesLost = fumblesLost;
    }

    @JsonProperty("risk")
    public Integer getRisk() {
        return risk;
    }

    @JsonProperty("risk")
    public void setRisk(Integer risk) {
        this.risk = risk;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("receiving_targets")
    public String getReceivingTargets() {
        return receivingTargets;
    }

    @JsonProperty("receiving_targets")
    public void setReceivingTargets(String receivingTargets) {
        this.receivingTargets = receivingTargets;
    }

    @JsonProperty("analyst_name")
    public String getAnalystName() {
        return analystName;
    }

    @JsonProperty("analyst_name")
    public void setAnalystName(String analystName) {
        this.analystName = analystName;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("fantasy_position")
    public String getFantasyPosition() {
        return fantasyPosition;
    }

    @JsonProperty("fantasy_position")
    public void setFantasyPosition(String fantasyPosition) {
        this.fantasyPosition = fantasyPosition;
    }

    @JsonProperty("team")
    public String getTeam() {
        return team;
    }

    @JsonProperty("team")
    public void setTeam(String team) {
        this.team = team;
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("bye_week")
    public String getByeWeek() {
        return byeWeek;
    }

    @JsonProperty("bye_week")
    public void setByeWeek(String byeWeek) {
        this.byeWeek = byeWeek;
    }

    @JsonProperty("udk")
    public String getUdk() {
        return udk;
    }

    @JsonProperty("udk")
    public void setUdk(String udk) {
        this.udk = udk;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @JsonProperty("adp")
    public String getAdp() {
        return adp;
    }

    @JsonProperty("adp")
    public void setAdp(String adp) {
        this.adp = adp;
    }

    @JsonProperty("adp_ppr")
    public String getAdpPpr() {
        return adpPpr;
    }

    @JsonProperty("adp_ppr")
    public void setAdpPpr(String adpPpr) {
        this.adpPpr = adpPpr;
    }

    @JsonProperty("adp_half_ppr")
    public String getAdpHalfPpr() {
        return adpHalfPpr;
    }

    @JsonProperty("adp_half_ppr")
    public void setAdpHalfPpr(String adpHalfPpr) {
        this.adpHalfPpr = adpHalfPpr;
    }

    @JsonProperty("headshot")
    public String getHeadshot() {
        return headshot;
    }

    @JsonProperty("headshot")
    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    @JsonProperty("tagged")
    public Boolean getTagged() {
        return tagged;
    }

    @JsonProperty("tagged")
    public void setTagged(Boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Projection.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("playerId");
        sb.append('=');
        sb.append(((this.playerId == null)?"<null>":this.playerId));
        sb.append(',');
        sb.append("analystId");
        sb.append('=');
        sb.append(((this.analystId == null)?"<null>":this.analystId));
        sb.append(',');
        sb.append("seasonType");
        sb.append('=');
        sb.append(((this.seasonType == null)?"<null>":this.seasonType));
        sb.append(',');
        sb.append("season");
        sb.append('=');
        sb.append(((this.season == null)?"<null>":this.season));
        sb.append(',');
        sb.append("passingAttempts");
        sb.append('=');
        sb.append(((this.passingAttempts == null)?"<null>":this.passingAttempts));
        sb.append(',');
        sb.append("passingYards");
        sb.append('=');
        sb.append(((this.passingYards == null)?"<null>":this.passingYards));
        sb.append(',');
        sb.append("passingTouchdowns");
        sb.append('=');
        sb.append(((this.passingTouchdowns == null)?"<null>":this.passingTouchdowns));
        sb.append(',');
        sb.append("passingCompletions");
        sb.append('=');
        sb.append(((this.passingCompletions == null)?"<null>":this.passingCompletions));
        sb.append(',');
        sb.append("rushingAttempts");
        sb.append('=');
        sb.append(((this.rushingAttempts == null)?"<null>":this.rushingAttempts));
        sb.append(',');
        sb.append("rushingYards");
        sb.append('=');
        sb.append(((this.rushingYards == null)?"<null>":this.rushingYards));
        sb.append(',');
        sb.append("rushingYardsPerAttempt");
        sb.append('=');
        sb.append(((this.rushingYardsPerAttempt == null)?"<null>":this.rushingYardsPerAttempt));
        sb.append(',');
        sb.append("rushingTouchdowns");
        sb.append('=');
        sb.append(((this.rushingTouchdowns == null)?"<null>":this.rushingTouchdowns));
        sb.append(',');
        sb.append("receptions");
        sb.append('=');
        sb.append(((this.receptions == null)?"<null>":this.receptions));
        sb.append(',');
        sb.append("receivingYards");
        sb.append('=');
        sb.append(((this.receivingYards == null)?"<null>":this.receivingYards));
        sb.append(',');
        sb.append("receivingYardsPerReception");
        sb.append('=');
        sb.append(((this.receivingYardsPerReception == null)?"<null>":this.receivingYardsPerReception));
        sb.append(',');
        sb.append("receivingTouchdowns");
        sb.append('=');
        sb.append(((this.receivingTouchdowns == null)?"<null>":this.receivingTouchdowns));
        sb.append(',');
        sb.append("interceptionsThrown");
        sb.append('=');
        sb.append(((this.interceptionsThrown == null)?"<null>":this.interceptionsThrown));
        sb.append(',');
        sb.append("fumblesLost");
        sb.append('=');
        sb.append(((this.fumblesLost == null)?"<null>":this.fumblesLost));
        sb.append(',');
        sb.append("risk");
        sb.append('=');
        sb.append(((this.risk == null)?"<null>":this.risk));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("updatedAt");
        sb.append('=');
        sb.append(((this.updatedAt == null)?"<null>":this.updatedAt));
        sb.append(',');
        sb.append("receivingTargets");
        sb.append('=');
        sb.append(((this.receivingTargets == null)?"<null>":this.receivingTargets));
        sb.append(',');
        sb.append("analystName");
        sb.append('=');
        sb.append(((this.analystName == null)?"<null>":this.analystName));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("fantasyPosition");
        sb.append('=');
        sb.append(((this.fantasyPosition == null)?"<null>":this.fantasyPosition));
        sb.append(',');
        sb.append("team");
        sb.append('=');
        sb.append(((this.team == null)?"<null>":this.team));
        sb.append(',');
        sb.append("number");
        sb.append('=');
        sb.append(((this.number == null)?"<null>":this.number));
        sb.append(',');
        sb.append("byeWeek");
        sb.append('=');
        sb.append(((this.byeWeek == null)?"<null>":this.byeWeek));
        sb.append(',');
        sb.append("udk");
        sb.append('=');
        sb.append(((this.udk == null)?"<null>":this.udk));
        sb.append(',');
        sb.append("slug");
        sb.append('=');
        sb.append(((this.slug == null)?"<null>":this.slug));
        sb.append(',');
        sb.append("adp");
        sb.append('=');
        sb.append(((this.adp == null)?"<null>":this.adp));
        sb.append(',');
        sb.append("adpPpr");
        sb.append('=');
        sb.append(((this.adpPpr == null)?"<null>":this.adpPpr));
        sb.append(',');
        sb.append("adpHalfPpr");
        sb.append('=');
        sb.append(((this.adpHalfPpr == null)?"<null>":this.adpHalfPpr));
        sb.append(',');
        sb.append("headshot");
        sb.append('=');
        sb.append(((this.headshot == null)?"<null>":this.headshot));
        sb.append(',');
        sb.append("tagged");
        sb.append('=');
        sb.append(((this.tagged == null)?"<null>":this.tagged));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
