package com.falifa.draftbuddy.ui.model.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleeperPlayerData {

	@JsonProperty("college")
	private String college;
	@JsonProperty("height")
	private String height;
	@JsonProperty("search_first_name")
	private String searchFirstName;
	@JsonProperty("search_full_name")
	private String searchFullName;
	@JsonProperty("weight")
	private String weight;
	@JsonProperty("stats_id")
	private Integer statsId;
	@JsonProperty("rotowire_id")
	private Integer rotowireId;
	@JsonProperty("high_school")
	private String highSchool;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("swish_id")
	private Integer swishId;
	@JsonProperty("injury_status")
	private String injuryStatus;
	@JsonProperty("espn_id")
	private Integer espnId;
	@JsonProperty("gsis_id")
	private String gsisId;
	@JsonProperty("sport")
	private String sport;
	@JsonProperty("player_id")
	private String playerId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("fantasy_data_id")
	private Integer fantasyDataId;
	@JsonProperty("position")
	private String position;
	@JsonProperty("team")
	private String team;
	@JsonProperty("birth_date")
	private String birthDate;
	@JsonProperty("years_exp")
	private Integer yearsExp;
	@JsonProperty("full_name")
	private String fullName;
	@JsonProperty("pandascore_id")
	private Object pandascoreId;
	@JsonProperty("rotoworld_id")
	private Integer rotoworldId;
	@JsonProperty("sportradar_id")
	private String sportradarId;
	@JsonProperty("search_last_name")
	private String searchLastName;
	@JsonProperty("depth_chart_position")
	private String depthChartPosition;
	@JsonProperty("news_updated")
	private Long newsUpdated;
	@JsonProperty("yahoo_id")
	private Integer yahooId;
	@JsonProperty("active")
	private Boolean active;
	@JsonProperty("hashtag")
	private String hashtag;
	@JsonProperty("age")
	private Integer age;
	@JsonProperty("depth_chart_order")
	private Integer depthChartOrder;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("search_rank")
	private Integer searchRank;
	@JsonProperty("number")
	private Integer number;

	private String fantasyProsId;

	public String getFantasyProsId() {
		return fantasyProsId;
	}

	public void setFantasyProsId(String fantasyProsId) {
		this.fantasyProsId = fantasyProsId;
	}

	@JsonProperty("college")
	public String getCollege() {
		return college;
	}

	@JsonProperty("college")
	public void setCollege(String college) {
		this.college = college;
	}

	@JsonProperty("height")
	public String getHeight() {
		return height;
	}

	@JsonProperty("height")
	public void setHeight(String height) {
		this.height = height;
	}

	@JsonProperty("search_first_name")
	public String getSearchFirstName() {
		return searchFirstName;
	}

	@JsonProperty("search_first_name")
	public void setSearchFirstName(String searchFirstName) {
		this.searchFirstName = searchFirstName;
	}

	@JsonProperty("search_full_name")
	public String getSearchFullName() {
		return searchFullName;
	}

	@JsonProperty("search_full_name")
	public void setSearchFullName(String searchFullName) {
		this.searchFullName = searchFullName;
	}

	@JsonProperty("weight")
	public String getWeight() {
		return weight;
	}

	@JsonProperty("weight")
	public void setWeight(String weight) {
		this.weight = weight;
	}

	@JsonProperty("stats_id")
	public Integer getStatsId() {
		return statsId;
	}

	@JsonProperty("stats_id")
	public void setStatsId(Integer statsId) {
		this.statsId = statsId;
	}

	@JsonProperty("rotowire_id")
	public Integer getRotowireId() {
		return rotowireId;
	}

	@JsonProperty("rotowire_id")
	public void setRotowireId(Integer rotowireId) {
		this.rotowireId = rotowireId;
	}

	@JsonProperty("high_school")
	public String getHighSchool() {
		return highSchool;
	}

	@JsonProperty("high_school")
	public void setHighSchool(String highSchool) {
		this.highSchool = highSchool;
	}

	@JsonProperty("first_name")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("first_name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("swish_id")
	public Integer getSwishId() {
		return swishId;
	}

	@JsonProperty("swish_id")
	public void setSwishId(Integer swishId) {
		this.swishId = swishId;
	}

	@JsonProperty("injury_status")
	public String getInjuryStatus() {
		return injuryStatus;
	}

	@JsonProperty("injury_status")
	public void setInjuryStatus(String injuryStatus) {
		this.injuryStatus = injuryStatus;
	}

	@JsonProperty("espn_id")
	public Integer getEspnId() {
		return espnId;
	}

	@JsonProperty("espn_id")
	public void setEspnId(Integer espnId) {
		this.espnId = espnId;
	}

	@JsonProperty("gsis_id")
	public String getGsisId() {
		return gsisId;
	}

	@JsonProperty("gsis_id")
	public void setGsisId(String gsisId) {
		this.gsisId = gsisId;
	}

	@JsonProperty("sport")
	public String getSport() {
		return sport;
	}

	@JsonProperty("sport")
	public void setSport(String sport) {
		this.sport = sport;
	}

	@JsonProperty("player_id")
	public String getPlayerId() {
		return playerId;
	}

	@JsonProperty("player_id")
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("fantasy_data_id")
	public Integer getFantasyDataId() {
		return fantasyDataId;
	}

	@JsonProperty("fantasy_data_id")
	public void setFantasyDataId(Integer fantasyDataId) {
		this.fantasyDataId = fantasyDataId;
	}

	@JsonProperty("position")
	public String getPosition() {
		return position;
	}

	@JsonProperty("position")
	public void setPosition(String position) {
		this.position = position;
	}

	@JsonProperty("team")
	public String getTeam() {
		return team;
	}

	@JsonProperty("team")
	public void setTeam(String team) {
		this.team = team;
	}

	@JsonProperty("birth_date")
	public String getBirthDate() {
		return birthDate;
	}

	@JsonProperty("birth_date")
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	@JsonProperty("years_exp")
	public Integer getYearsExp() {
		return yearsExp;
	}

	@JsonProperty("years_exp")
	public void setYearsExp(Integer yearsExp) {
		this.yearsExp = yearsExp;
	}

	@JsonProperty("full_name")
	public String getFullName() {
		return fullName;
	}

	@JsonProperty("full_name")
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@JsonProperty("pandascore_id")
	public Object getPandascoreId() {
		return pandascoreId;
	}

	@JsonProperty("pandascore_id")
	public void setPandascoreId(Object pandascoreId) {
		this.pandascoreId = pandascoreId;
	}

	@JsonProperty("rotoworld_id")
	public Integer getRotoworldId() {
		return rotoworldId;
	}

	@JsonProperty("rotoworld_id")
	public void setRotoworldId(Integer rotoworldId) {
		this.rotoworldId = rotoworldId;
	}

	@JsonProperty("sportradar_id")
	public String getSportradarId() {
		return sportradarId;
	}

	@JsonProperty("sportradar_id")
	public void setSportradarId(String sportradarId) {
		this.sportradarId = sportradarId;
	}

	@JsonProperty("search_last_name")
	public String getSearchLastName() {
		return searchLastName;
	}

	@JsonProperty("search_last_name")
	public void setSearchLastName(String searchLastName) {
		this.searchLastName = searchLastName;
	}

	@JsonProperty("depth_chart_position")
	public String getDepthChartPosition() {
		return depthChartPosition;
	}

	@JsonProperty("depth_chart_position")
	public void setDepthChartPosition(String depthChartPosition) {
		this.depthChartPosition = depthChartPosition;
	}

	@JsonProperty("news_updated")
	public Long getNewsUpdated() {
		return newsUpdated;
	}

	@JsonProperty("news_updated")
	public void setNewsUpdated(Long newsUpdated) {
		this.newsUpdated = newsUpdated;
	}

	@JsonProperty("yahoo_id")
	public Integer getYahooId() {
		return yahooId;
	}

	@JsonProperty("yahoo_id")
	public void setYahooId(Integer yahooId) {
		this.yahooId = yahooId;
	}

	@JsonProperty("active")
	public Boolean getActive() {
		return active;
	}

	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonProperty("hashtag")
	public String getHashtag() {
		return hashtag;
	}

	@JsonProperty("hashtag")
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	@JsonProperty("age")
	public Integer getAge() {
		return age;
	}

	@JsonProperty("age")
	public void setAge(Integer age) {
		this.age = age;
	}

	@JsonProperty("depth_chart_order")
	public Integer getDepthChartOrder() {
		return depthChartOrder;
	}

	@JsonProperty("depth_chart_order")
	public void setDepthChartOrder(Integer depthChartOrder) {
		this.depthChartOrder = depthChartOrder;
	}

	@JsonProperty("last_name")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("last_name")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("search_rank")
	public Integer getSearchRank() {
		return searchRank;
	}

	@JsonProperty("search_rank")
	public void setSearchRank(Integer searchRank) {
		this.searchRank = searchRank;
	}

	@JsonProperty("number")
	public Integer getNumber() {
		return number;
	}

	@JsonProperty("number")
	public void setNumber(Integer number) {
		this.number = number;
	}

}
