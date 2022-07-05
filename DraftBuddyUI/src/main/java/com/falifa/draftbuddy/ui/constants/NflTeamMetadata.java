package com.falifa.draftbuddy.ui.constants;

public enum NflTeamMetadata {
	
	BEARS("Bears", "CHI", "Chicago", false, 33),
	BENGALS("Bengals", "CIN", "Cincinnati", false, 33),
	BILLS("Bills", "BUF", "Buffalo", false, 33),
	BRONCOS("Broncos", "DEN", "Denver", false, 33),
	BROWNS("Browns", "CLE", "Cleveland", true, 33),
	BUCCANEERS("Buccaneers", "TB", "Tampa Bay", false, 33),
	CARDINALS("Cardinals", "ARI", "Arizona", false, 33),
	CHARGERS("Chargers", "LAC", "Las Angeles", false, 33),
	CHIEFS("Chiefs", "KC", "Kansas City", false, 33),
	COLTS("Colts", "IND", "Indianapolis", false, 33),
	COMMANDERS("Commanders", "WAS", "Washington", false, 33),
	COWBOYS("Cowboys", "DAL", "Dallas", false, 33),
	DOLPHINS("Dolphins", "MIA", "Miami", false, 33),
	EAGLES("Eagles", "PHI", "Philadelphia", false, 33),
	FALCONS("Falcons", "ATL", "Atlanta", false, 33),
	FORTY_NINERS("49ers", "SF", "San Francisco", false, 33),
	GIANTS("Giants", "NYG", "New York", false, 33),
	JAGUARS("Jaguars", "JAC", "Jacksonville", false, 33),
	JETS("Jets", "NYJ", "New York", false, 33),
	LIONS("Lions", "DET", "Detroit", false, 33),
	PACKERS("Packers", "GB", "Green Bay", false, 33),
	PANTHERS("Panthers", "CAR", "Carolina", false, 33),
	PATRIOTS("Patriots", "NE", "New England", false, 33),
	RAIDERS("Raiders", "LV", "Las Vegas", false, 33),
	RAMS("Rams", "LAR", "Las Angeles", false, 33),
	RAVENS("Ravens", "BAL", "Baltimore", false, 33),
	SAINTS("Saints", "NO", "New Orleans", false, 33),
	SEAHAWKS("Seahawks", "SEA", "Seattle", false, 33),
	STEELERS("Steelers", "PIT", "Pittsburgh", false, 33),
	TEXANS("Texans", "HOU", "Houston", false, 33),
	TITANS("Titans", "TEN", "Tennessee", false, 33),
	VIKINGS("Vikings", "MIN", "Minnesota", false, 33),
	FREE_AGENTS("Free Agent", "FA", "Free Agent", false, 33);
	
	private String mascot;
	private String abbreviation;
	private String city;
	private boolean newCoach;
	private int oLineRank;
	
	NflTeamMetadata(String mascot, String abbreviation, String city, boolean newCoach, int oLineRank) {
		this.mascot = mascot;
		this.abbreviation = abbreviation;
		this.city = city;
		this.newCoach = newCoach;
		this.oLineRank = oLineRank;
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

	public int getoLineRank() {
		return oLineRank;
	}

	public void setoLineRank(int oLineRank) {
		this.oLineRank = oLineRank;
	}
	
}
