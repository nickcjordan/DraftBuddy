package com.falifa.draftbuddy.ui.constants;

public enum NflTeam {
	
	DOLPHINS("Dolphins", "MIA", "Miami"),
	RAIDERS("Raiders", "OAK", "Oakland"),
	COWBOYS("Cowboys", "DAL", "Dallas"),
	PACKERS("Packers", "GB", "Green Bay"),
	TEXANS("Texans", "HOU", "Houston"),
	BRONCOS("Broncos", "DEN", "Denver"),
	SAINTS("Saints", "NO", "New Orleans"),
	TITANS("Titans", "TEN", "Tennessee"),
	LIONS("Lions", "DET", "Detroit"),
	STEELERS("Steelers", "PIT", "Pittsburgh"),
	BEARS("Bears", "CHI", "Chicago"),
	VIKINGS("Vikings", "MIN", "Minnesota"),
	JAGUARS("Jaguars", "JAC", "Jacksonville"),
	PANTHERS("Panthers", "CAR", "Carolina"),
	BUCCANEERS("Buccaneers", "TB", "Tampa Bay"),
	CHIEFS("Chiefs", "KC", "Kansas City"),
	CHARGERS("Chargers", "LAC", "Las Angeles"),
	FORTY_NINERS("49ers", "SF", "San Francisco"),
	RAVENS("Ravens", "BAL", "Baltimore"),
	COLTS("Colts", "IND", "Indianapolis"),
	BILLS("Bills", "BUF", "Buffalo"),
	CARDINALS("Cardinals", "ARI", "Arizona"),
	BROWNS("Browns", "CLE", "Cleveland"),
	REDSKINS("Redskins", "WAS", "Washington"),
	FALCONS("Falcons", "ATL", "Atlanta"),
	GIANTS("Giants", "NYG", "New York"),
	JETS("Jets", "NYJ", "New York"),
	RAMS("Rams", "LAR", "Las Angeles"),
	SEAHAWKS("Seahawks", "SEA", "Seattle"),
	BENGALS("Bengals", "CIN", "Cincinnati"),
	EAGLES("Eagles", "PHI", "Philadelphia"),
	PATRIOTS("Patriots", "NE", "New England"),
	FREE_AGENTS("Free Agent", "FA", "Free Agent");
	
	private String mascot;
	private String abbreviation;
	private String city;
	
	NflTeam(String mascot, String abbreviation, String city) {
		this.mascot = mascot;
		this.abbreviation = abbreviation;
		this.city = city;
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
	
	public static NflTeam findNflTeamFromAbbreviation(String abbreviation) {
		NflTeam selectedTeam = null;
		for (NflTeam team : NflTeam.values()) {
			if (team.getAbbreviation().equalsIgnoreCase(abbreviation)) {
				selectedTeam = team;
			}
		}
		return selectedTeam;
	}
	
	public String getFullName() {
		return this.city + " " + this.mascot;
	}
	
}
