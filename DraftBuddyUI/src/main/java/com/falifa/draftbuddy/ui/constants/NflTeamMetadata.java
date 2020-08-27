package com.falifa.draftbuddy.ui.constants;

public enum NflTeamMetadata {
	
	BEARS("Bears", "CHI", "Chicago", false, 23),
	BENGALS("Bengals", "CIN", "Cincinnati", false, 28),
	BILLS("Bills", "BUF", "Buffalo", false, 17),
	BRONCOS("Broncos", "DEN", "Denver", false, 13),
	BROWNS("Browns", "CLE", "Cleveland", true, 18),
	BUCCANEERS("Buccaneers", "TB", "Tampa Bay", false, 12),
	CARDINALS("Cardinals", "ARI", "Arizona", false, 22),
	CHARGERS("Chargers", "LAC", "Las Angeles", false, 27),
	CHIEFS("Chiefs", "KC", "Kansas City", false, 15),
	COWBOYS("Cowboys", "DAL", "Dallas", true, 1),
	COLTS("Colts", "IND", "Indianapolis", false, 2),
	DOLPHINS("Dolphins", "MIA", "Miami", false, 32),
	EAGLES("Eagles", "PHI", "Philadelphia", false, 5),
	FALCONS("Falcons", "ATL", "Atlanta", false, 11),
	FORTY_NINERS("49ers", "SF", "San Francisco", false, 9),
	GIANTS("Giants", "NYG", "New York", true, 10),
	JAGUARS("Jaguars", "JAC", "Jacksonville", false, 25),
	JETS("Jets", "NYJ", "New York", false, 24),
	LIONS("Lions", "DET", "Detroit", false, 8),
	PACKERS("Packers", "GB", "Green Bay", false, 6),
	PANTHERS("Panthers", "CAR", "Carolina", true, 29),
	PATRIOTS("Patriots", "NE", "New England", false, 21),
	RAIDERS("Raiders", "LV", "Las Vegas", false, 14),
	RAMS("Rams", "LAR", "Las Angeles", false, 30),
	RAVENS("Ravens", "BAL", "Baltimore", false, 3),
	REDSKINS("Redskins", "WAS", "Washington", true, 31),
	SAINTS("Saints", "NO", "New Orleans", false, 4),
	SEAHAWKS("Seahawks", "SEA", "Seattle", false, 26),
	STEELERS("Steelers", "PIT", "Pittsburgh", false, 7),
	TEXANS("Texans", "HOU", "Houston", false, 19),
	TITANS("Titans", "TEN", "Tennessee", false, 20),
	VIKINGS("Vikings", "MIN", "Minnesota", false, 16),
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
