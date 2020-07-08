package com.falifa.draftbuddy.ui.constants;

public enum NflTeamMetadata {
	
	DOLPHINS("Dolphins", "MIA", "Miami", false),
	RAIDERS("Raiders", "LV", "Las Vegas", false),
	COWBOYS("Cowboys", "DAL", "Dallas", true),
	PACKERS("Packers", "GB", "Green Bay", false),
	TEXANS("Texans", "HOU", "Houston", false),
	BRONCOS("Broncos", "DEN", "Denver", false),
	SAINTS("Saints", "NO", "New Orleans", false),
	TITANS("Titans", "TEN", "Tennessee", false),
	LIONS("Lions", "DET", "Detroit", false),
	STEELERS("Steelers", "PIT", "Pittsburgh", false),
	BEARS("Bears", "CHI", "Chicago", false),
	VIKINGS("Vikings", "MIN", "Minnesota", false),
	JAGUARS("Jaguars", "JAC", "Jacksonville", false),
	PANTHERS("Panthers", "CAR", "Carolina", true),
	BUCCANEERS("Buccaneers", "TB", "Tampa Bay", false),
	CHIEFS("Chiefs", "KC", "Kansas City", false),
	CHARGERS("Chargers", "LAC", "Las Angeles", false),
	FORTY_NINERS("49ers", "SF", "San Francisco", false),
	RAVENS("Ravens", "BAL", "Baltimore", false),
	COLTS("Colts", "IND", "Indianapolis", false),
	BILLS("Bills", "BUF", "Buffalo", false),
	CARDINALS("Cardinals", "ARI", "Arizona", false),
	BROWNS("Browns", "CLE", "Cleveland", true),
	REDSKINS("Redskins", "WAS", "Washington", true),
	FALCONS("Falcons", "ATL", "Atlanta", false),
	GIANTS("Giants", "NYG", "New York", true),
	JETS("Jets", "NYJ", "New York", false),
	RAMS("Rams", "LAR", "Las Angeles", false),
	SEAHAWKS("Seahawks", "SEA", "Seattle", false),
	BENGALS("Bengals", "CIN", "Cincinnati", false),
	EAGLES("Eagles", "PHI", "Philadelphia", false),
	PATRIOTS("Patriots", "NE", "New England", false),
	FREE_AGENTS("Free Agent", "FA", "Free Agent", false);
	
	private String mascot;
	private String abbreviation;
	private String city;
	private boolean newCoach;
	
	NflTeamMetadata(String mascot, String abbreviation, String city, boolean newCoach) {
		this.mascot = mascot;
		this.abbreviation = abbreviation;
		this.city = city;
		this.newCoach = newCoach;
	}

	public String getMascot() {
		return mascot;
	}

	public void setMascot(String mascot) {
		this.mascot = mascot;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public boolean isNewCoach() {
		return newCoach;
	}

	public void setNewCoach(boolean newCoach) {
		this.newCoach = newCoach;
	}

	public static NflTeamMetadata findNflTeamFromAbbreviation(String abbreviation) {
		NflTeamMetadata selectedTeam = null;
		for (NflTeamMetadata team : NflTeamMetadata.values()) {
			if (team.getAbbreviation().equalsIgnoreCase(abbreviation)) {
				selectedTeam = team;
			}
		}
		return selectedTeam;
	}
	
	public static NflTeamMetadata findNflTeamFromFullText(String text) {
		NflTeamMetadata selectedTeam = null;
		for (NflTeamMetadata team : NflTeamMetadata.values()) {
			if (text.toLowerCase().contains(team.getCity().toLowerCase()) || text.toLowerCase().contains(team.getMascot().toLowerCase())) {
				selectedTeam = team;
			}
		}
		return selectedTeam;
	}
	
	public String getFullName() {
		return this.city + " " + this.mascot;
	}
	
}
