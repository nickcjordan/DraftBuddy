package com.falifa.draftbuddy.ui.prep.scraper.webjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import static com.falifa.draftbuddy.ui.constants.Position.*;

import com.falifa.draftbuddy.ui.constants.Position;
import com.falifa.draftbuddy.ui.draft.compare.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.draft.compare.FFBallerOverallPointsComparator;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.FFBallersConsolidatedProjection;
import com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers.Projection;

@Component
public class FFBallersDataConsolidator {

	public FFBallersConsolidatedProjection buildConsolidatedProjection(List<Projection> projectionsForPlayer) {
		
		int risk = 0;
		int fumblesLost = 0;
		int passingTD = 0;
		int interceptions = 0;
		int passYards = 0;
		int recTD = 0;
		int recYards = 0;
		int rec = 0;
		int rushTD = 0;
		int rushYards = 0;
		
		for (Projection p : projectionsForPlayer) {
			risk += Optional.ofNullable(p.getRisk()).orElse(0);
			fumblesLost += Optional.ofNullable(p.getFumblesLost()).orElse(0);
			passingTD += Optional.ofNullable(p.getPassingTouchdowns()).orElse(0);
			interceptions += Optional.ofNullable(p.getInterceptionsThrown()).orElse(0);
			passYards += Optional.ofNullable(p.getPassingYards()).orElse(0);
			recTD += Optional.ofNullable(p.getReceivingTouchdowns()).orElse(0);
			recYards += Optional.ofNullable(p.getReceivingYards()).orElse(0);
			rec += Optional.ofNullable(p.getReceptions()).orElse(0);
			rushTD += Optional.ofNullable(p.getRushingTouchdowns()).orElse(0);
			rushYards += Optional.ofNullable(p.getRushingYards()).orElse(0);
		}
		
		FFBallersConsolidatedProjection cp = new FFBallersConsolidatedProjection();
		cp.setPlayerId(projectionsForPlayer.get(0).getPlayerId());
		cp.setName(projectionsForPlayer.get(0).getName());
		cp.setPosition(Position.get(projectionsForPlayer.get(0).getFantasyPosition()));
		cp.setRisk(avg(risk));
		cp.setFumblesLost(avg(fumblesLost));
		cp.setPassingTouchdowns(avg(passingTD));
		cp.setInterceptionsThrown(avg(interceptions));
		cp.setPassingYards(avg(passYards));
		cp.setReceivingTouchdowns(avg(recTD));
		cp.setReceivingYards(avg(recYards));
		cp.setReceptions(avg(rec));
		cp.setRushingTouchdowns(avg(rushTD));
		cp.setRushingYards(avg(rushYards));
		cp.setOverallPoints(calculateOverallPoints(cp));
		cp.setAvgPoints(round(cp.getOverallPoints()/17.0, 1));
		return cp;
	}

	private int calculateOverallPoints(FFBallersConsolidatedProjection cp) {
		double points = 0;
		points += cp.getFumblesLost() * (-2);
		points += cp.getInterceptionsThrown() * (-2);
		points += cp.getPassingTouchdowns() * 4;
		points += cp.getReceivingTouchdowns() * 6;
		points += cp.getReceivingYards() * 0.1;
		points += cp.getReceptions() * 1;
		points += cp.getRushingTouchdowns() * 6;
		points += cp.getRushingYards() * 0.1;
		points += cp.getPassingYards() * 0.05;
		return new Double(points).intValue();
	}

	private Integer avg(int risk) {
		return risk / 3;
	}

	public void addRanks(Map<String, FFBallersConsolidatedProjection> consolidatedProjections) {
		List<FFBallersConsolidatedProjection> projections = new ArrayList<FFBallersConsolidatedProjection>(consolidatedProjections.values());
		
		for (Position p : Position.values()) {
			addRanks(projections.stream().filter(x -> x.getPosition().equals(p)).collect(Collectors.toList()));
		}
		
		
		List<FFBallersConsolidatedProjection> flexPlayers = projections.stream().filter(x -> (x.getPosition().equals(Position.RUNNINGBACK) || x.getPosition().equals(Position.WIDERECEIVER) || x.getPosition().equals(Position.TIGHTEND))).collect(Collectors.toList());
		Collections.sort(flexPlayers, new FFBallerOverallPointsComparator());
		
		
		double highest = (double) flexPlayers.get(0).getOverallPoints();
		for (int i = 1; i <= flexPlayers.size(); i++) {
			FFBallersConsolidatedProjection p = flexPlayers.get(i-1);
			p.setFlexRank(i);
			double ovr = p.getOverallPoints();
			double val = (ovr / highest);
			double valHuned = val * 100;
			int grade = (int) Math.round(valHuned);
			p.setFlexGrade(grade);
		}
		
		
//		Map<Position, Integer> ranks = new HashMap<Position, Integer>();
//		ranks.put(QUARTERBACK, 1);
//		ranks.put(RUNNINGBACK, 1);
//		ranks.put(WIDERECEIVER, 1);
//		ranks.put(TIGHTEND, 1);
//		for (FFBallersConsolidatedProjection p : projections) {
//			try {
//				Integer currentPositionRank = ranks.get(p.getPosition());
//				p.setPositionRank(currentPositionRank);
//				ranks.put(p.getPosition(), currentPositionRank + 1);
//			} catch (NullPointerException e) {
//				// do nothing because its a kicker or defense
//			}
//		}
	}

	private void addRanks(List<FFBallersConsolidatedProjection> projections) {
		Collections.sort(projections, new FFBallerOverallPointsComparator());
		int rank = 1;
		for (FFBallersConsolidatedProjection p : projections) {
			try {
				p.setPositionRank(rank);
				rank = rank + 1;
			} catch (NullPointerException e) {
				// do nothing because its a kicker or defense
			}
		}
	}
	
	private double round(double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}


}
