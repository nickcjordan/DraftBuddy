
package com.falifa.draftbuddy.ui.prep.scraper.webjson.model;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sport",
    "type",
    "ranking_type_name",
    "year",
    "week",
    "position_id",
    "scoring",
    "filters",
    "count",
    "total_experts",
    "last_updated",
    "players",
    "experts_available",
    "accessed"
})
@Generated("jsonschema2pojo")
public class ECRData {

    @JsonProperty("sport")
    private String sport;
    @JsonProperty("type")
    private String type;
    @JsonProperty("ranking_type_name")
    private String rankingTypeName;
    @JsonProperty("year")
    private String year;
    @JsonProperty("week")
    private String week;
    @JsonProperty("position_id")
    private String positionId;
    @JsonProperty("scoring")
    private String scoring;
    @JsonProperty("filters")
    private String filters;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("total_experts")
    private Integer totalExperts;
    @JsonProperty("last_updated")
    private String lastUpdated;
    @JsonProperty("players")
    private List<Player> players = null;
    @JsonProperty("experts_available")
    private ExpertsAvailable expertsAvailable;
    @JsonProperty("accessed")
    private String accessed;

    @JsonProperty("sport")
    public String getSport() {
        return sport;
    }

    @JsonProperty("sport")
    public void setSport(String sport) {
        this.sport = sport;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("ranking_type_name")
    public String getRankingTypeName() {
        return rankingTypeName;
    }

    @JsonProperty("ranking_type_name")
    public void setRankingTypeName(String rankingTypeName) {
        this.rankingTypeName = rankingTypeName;
    }

    @JsonProperty("year")
    public String getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(String year) {
        this.year = year;
    }

    @JsonProperty("week")
    public String getWeek() {
        return week;
    }

    @JsonProperty("week")
    public void setWeek(String week) {
        this.week = week;
    }

    @JsonProperty("position_id")
    public String getPositionId() {
        return positionId;
    }

    @JsonProperty("position_id")
    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @JsonProperty("scoring")
    public String getScoring() {
        return scoring;
    }

    @JsonProperty("scoring")
    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    @JsonProperty("filters")
    public String getFilters() {
        return filters;
    }

    @JsonProperty("filters")
    public void setFilters(String filters) {
        this.filters = filters;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("total_experts")
    public Integer getTotalExperts() {
        return totalExperts;
    }

    @JsonProperty("total_experts")
    public void setTotalExperts(Integer totalExperts) {
        this.totalExperts = totalExperts;
    }

    @JsonProperty("last_updated")
    public String getLastUpdated() {
        return lastUpdated;
    }

    @JsonProperty("last_updated")
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @JsonProperty("players")
    public List<Player> getPlayers() {
        return players;
    }

    @JsonProperty("players")
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @JsonProperty("experts_available")
    public ExpertsAvailable getExpertsAvailable() {
        return expertsAvailable;
    }

    @JsonProperty("experts_available")
    public void setExpertsAvailable(ExpertsAvailable expertsAvailable) {
        this.expertsAvailable = expertsAvailable;
    }

    @JsonProperty("accessed")
    public String getAccessed() {
        return accessed;
    }

    @JsonProperty("accessed")
    public void setAccessed(String accessed) {
        this.accessed = accessed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ECRData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("sport");
        sb.append('=');
        sb.append(((this.sport == null)?"<null>":this.sport));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("rankingTypeName");
        sb.append('=');
        sb.append(((this.rankingTypeName == null)?"<null>":this.rankingTypeName));
        sb.append(',');
        sb.append("year");
        sb.append('=');
        sb.append(((this.year == null)?"<null>":this.year));
        sb.append(',');
        sb.append("week");
        sb.append('=');
        sb.append(((this.week == null)?"<null>":this.week));
        sb.append(',');
        sb.append("positionId");
        sb.append('=');
        sb.append(((this.positionId == null)?"<null>":this.positionId));
        sb.append(',');
        sb.append("scoring");
        sb.append('=');
        sb.append(((this.scoring == null)?"<null>":this.scoring));
        sb.append(',');
        sb.append("filters");
        sb.append('=');
        sb.append(((this.filters == null)?"<null>":this.filters));
        sb.append(',');
        sb.append("count");
        sb.append('=');
        sb.append(((this.count == null)?"<null>":this.count));
        sb.append(',');
        sb.append("totalExperts");
        sb.append('=');
        sb.append(((this.totalExperts == null)?"<null>":this.totalExperts));
        sb.append(',');
        sb.append("lastUpdated");
        sb.append('=');
        sb.append(((this.lastUpdated == null)?"<null>":this.lastUpdated));
        sb.append(',');
        sb.append("players");
        sb.append('=');
        sb.append(((this.players == null)?"<null>":this.players));
        sb.append(',');
        sb.append("expertsAvailable");
        sb.append('=');
        sb.append(((this.expertsAvailable == null)?"<null>":this.expertsAvailable));
        sb.append(',');
        sb.append("accessed");
        sb.append('=');
        sb.append(((this.accessed == null)?"<null>":this.accessed));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
