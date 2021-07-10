package com.falifa.draftbuddy.ui.prep.scraper.webjson;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.RankMetadata;
import com.falifa.draftbuddy.ui.prep.NFLTeamCache;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ECRData;

@Component
public class WebJsonPlayerConverter {
	
	private static final String DEFAULT_MAX_ADP = "1000";
	private static final Logger log = LoggerFactory.getLogger(WebJsonPlayerConverter.class);

	public Map<String, Player> convertToPlayerData(ECRData ecrData) {
		Map<String, Player> players = new HashMap<String, Player>();
		for (com.falifa.draftbuddy.ui.prep.scraper.webjson.model.Player playerData : ecrData.getPlayers()) {
			try {
				Player p = buildPlayer(playerData);
				players.put(p.getFantasyProsId(), p);
			} catch (Exception e) {
				log.error("ERROR building player :: name=" + playerData.getPlayerName()  + " :: id=" + playerData.getPlayerId() + " :: error=" + e.toString());
			}
		}
		return players;
	}

	private Player buildPlayer(com.falifa.draftbuddy.ui.prep.scraper.webjson.model.Player data) {
		Player p = new Player();
		p.setTier(data.getTier());
		p.setBye(data.getPlayerByeWeek());
		p.setFantasyProsId(String.valueOf(data.getPlayerId()));
		p.setPlayerName(data.getPlayerName());
		p.setTeam(NflTeamMetadata.findNflTeamFromAbbreviation(data.getPlayerTeamId()));
		p.setPosition(Position.get(data.getPlayerPositionId()));
		if (p.getPosition().equals(Position.DEFENSE)) {
			NFLTeamCache.addTeamToNfl(p.getFantasyProsId(), p.getTeam());
		}
		p.setRankMetadata(buildRankMetadata(data, p.getPosition().getAbbrev()));
		return p;
	}

	private RankMetadata buildRankMetadata(com.falifa.draftbuddy.ui.prep.scraper.webjson.model.Player data, String posAbbrev) {
		RankMetadata rank = new RankMetadata();
		rank.setOverallRank(String.valueOf(data.getRankEcr()));
		rank.setWorstRank(String.valueOf(data.getRankMax()));
		rank.setAvgRank(String.valueOf(data.getRankAve()));
		rank.setBestRank(String.valueOf(data.getRankMin()));
		rank.setPositionRank(data.getPosRank().replace(posAbbrev, ""));
		rank.setStdDev(String.valueOf(data.getRankStd()));
//		rank.setRookieRanking(String.valueOf(data.get));
//		rank.setVsAdp(String.valueOf(data.get));
		rank.setAdp(DEFAULT_MAX_ADP);
		return rank;
	}

}
