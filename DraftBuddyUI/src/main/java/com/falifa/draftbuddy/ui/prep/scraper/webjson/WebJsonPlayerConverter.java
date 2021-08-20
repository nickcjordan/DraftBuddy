package com.falifa.draftbuddy.ui.prep.scraper.webjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.constants.NflTeamMetadata;
import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.RankMetadata;
import com.falifa.draftbuddy.ui.prep.NFLTeamCache;
import com.falifa.draftbuddy.ui.prep.scraper.DataPopulatorHelper;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ECRData;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.FFBallersAPIData;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.FFBallersConsolidatedProjection;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.PlayerInfo;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.Projection;

@Component
public class WebJsonPlayerConverter {
	
	private static final String DEFAULT_MAX_ADP = "1000";
	private static final Logger log = LoggerFactory.getLogger(WebJsonPlayerConverter.class);
	
	@Autowired
	private DataPopulatorHelper dataHelper;
	
	@Autowired
	private FFBallersDataConsolidator dataConsolidator;

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

	public Map<String, Player> convertToPlayerData(FFBallersAPIData ballersData) {
		// loop through and put all projections in a map with <playerId, List<projection>> 
		Map<String, List<Projection>> projections = aggregateProjectionsByPlayer(ballersData);
			
		
		// loop through and consolidate the 3 projections per player into one ConsolidatedProjection
		Map<String, FFBallersConsolidatedProjection> consolidatedProjections = new HashMap<String, FFBallersConsolidatedProjection>();
		for (List<Projection> projectionsForPlayer : projections.values()) {
			FFBallersConsolidatedProjection consolidatedProjection = dataConsolidator.buildConsolidatedProjection(projectionsForPlayer);
			consolidatedProjections.put(consolidatedProjection.getPlayerId(), consolidatedProjection);
		}
		
		// loop through and put all "Essential"s aka PlayerInfo in a map with <playerId, PlayerInfo>
		List<PlayerInfo> notFoundPlayers = new ArrayList<PlayerInfo>();
		for (PlayerInfo info : ballersData.getEssentials().values()) {
			if (consolidatedProjections.containsKey(info.getPlayerId())) {
				FFBallersConsolidatedProjection consolidatedProjection = consolidatedProjections.get(info.getPlayerId());
				consolidatedProjection.setBlurb(info.getBlurb());
				consolidatedProjection.setDynastyBlurb(info.getDynastyBlurb());
			} else {
				notFoundPlayers.add(info);
			}
		}
		log.info("Finished parsing FFBallers API info :: \"Essential\" players not found in FFBallers Projections: ids={}", notFoundPlayers.stream().map(x -> x.getId()).collect(Collectors.joining(", ")));
		
		// add position ranks and overall ranks
		dataConsolidator.addRanks(consolidatedProjections);
			
		// loop through ConsolidatedProjections, find the player, and add stats
		Map<String, Player> players = new HashMap<String, Player>();
		for (FFBallersConsolidatedProjection projection : consolidatedProjections.values()) {
			try {
				Player player = dataHelper.getPlayerFromName(projection.getName());
				if (player != null) {
					player.setFfBallersPlayerProjection(projection);
					players.put(player.getFantasyProsId(), player);
				} else {
					log.error("ERROR updating player with FFBallers stats :: DID NOT FIND PLAYER :: name=" + projection.getName()  + " :: id=" + projection.getPlayerId() );
				}
			} catch (Exception e) {
				log.error("ERROR updating player with FFBallers stats :: name=" + projection.getName()  + " :: id=" + projection.getPlayerId() + " :: error=" + e.toString());
			}
		}
		
		return players;
	}


	private Map<String, List<Projection>> aggregateProjectionsByPlayer(FFBallersAPIData ballersData) {
		Map<String, List<Projection>> projections = new HashMap<String, List<Projection>> ();
		for (Projection projection : ballersData.getProjections()) {
			if (projections.containsKey(projection.getPlayerId())) {
				List<Projection> projectionsForPlayer = projections.get(projection.getPlayerId());
				projectionsForPlayer.add(projection);
			} else {
				List<Projection> projectionsForPlayer = new ArrayList<Projection>();
				projectionsForPlayer.add(projection);
				projections.put(projection.getPlayerId(), projectionsForPlayer);
			}
		}
		return projections;
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
