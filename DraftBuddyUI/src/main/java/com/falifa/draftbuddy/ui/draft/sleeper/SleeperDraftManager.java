package com.falifa.draftbuddy.ui.draft.sleeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftPick;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftState;
import com.falifa.draftbuddy.ui.draft.compare.DraftSelectionOrderComparator;
import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.model.DraftPick;
import com.falifa.draftbuddy.ui.model.Drafter;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.team.Team;
import com.falifa.draftbuddy.ui.prep.data.ModelUpdater;
import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.prep.scraper.TeammateTO;

@Component
public class SleeperDraftManager {
	
	
	private static final Logger log = LoggerFactory.getLogger(SleeperDraftManager.class);
	
	@Autowired
	private SleeperAPIManager api;
	
	@Autowired
	private DraftState draftState;
	
	public void updateDraftStateWithSleeperPicks(SleeperDraftState sleeperState) {
		List<SleeperDraftPick> allPicks = api.getDraftPicks(sleeperState.getDraft().getDraftId());
		int lastPickNumber = Math.min(draftState.getDraftPicks().size(), allPicks.size());
		List<SleeperDraftPick> unprocessedPicks = 
				lastPickNumber == 0 ? allPicks : 
				allPicks.size() == lastPickNumber ? new ArrayList<SleeperDraftPick>() 
				: allPicks.subList(lastPickNumber, allPicks.size());
		if (CollectionUtils.isEmpty(unprocessedPicks)) {
			return;
		}
		
		for (SleeperDraftPick pick : unprocessedPicks) {
			int drafterIndex = pick.getDraftSlot() - 1;
			Player player = NFLTeamManager.getPlayerBySleeperId(pick.getPlayerId());
			Drafter drafter = draftState.getDraft().getDrafters().get(drafterIndex);
			draftState.getDraftPicks().add(draftPlayer(drafter, player, pick, sleeperState));
		}
		int nextDraftPickNumber = allPicks.size() + 1;
		Collections.sort(draftState.getDraftPicks(), new DraftSelectionOrderComparator());
		
		for (Drafter drafter : draftState.draft.getDrafters()) {
			if (drafter.getDraftPickIndices().contains(nextDraftPickNumber)) {
				draftState.setCurrentDrafter(drafter);
			}
		}
		
		NFLTeamManager.setCurrentPlayerValue(nextDraftPickNumber);
		
		int drafterCount = draftState.getDraft().getDrafters().size();
		int draftPickRoundSpecificIndex = (allPicks.size() % drafterCount);
		if (draftPickRoundSpecificIndex == 0) {
			draftPickRoundSpecificIndex = drafterCount;
		}
		draftState.setDraftOrderIndex(draftPickRoundSpecificIndex);
		draftState.setPickNumber(nextDraftPickNumber);
		int nextPickRoundNumber =  Math.floorDiv(allPicks.size(), drafterCount) + 1;
		draftState.setRoundNum(nextPickRoundNumber);
		
		
	}
	
	private DraftPick draftPlayer(Drafter drafter, Player draftedPlayer, SleeperDraftPick pick, SleeperDraftState sleeperState) {
		draftedPlayer.getDraftingDetails().setRoundDrafted(pick.getRound());
		drafter.getDraftedTeam().addPlayer(draftedPlayer);
		draftedPlayer.getDraftingDetails().setAvailable(false);
		for (TeammateTO teammate : draftedPlayer.getDraftingDetails().getPositionTeammates()) {
			Player handcuff = NFLTeamManager.getPlayerById(teammate.getId());
			handcuff.getDraftingDetails().setHandcuff(true);
		}
		int drafterCount = sleeperState.getDrafters().size();
		int draftPickRoundSpecificIndex = (pick.getPickNo() % drafterCount);
		if (draftPickRoundSpecificIndex == 0) {
			draftPickRoundSpecificIndex = drafterCount;
		}
		return new DraftPick(pick.getPickNo(), pick.getRound(), draftPickRoundSpecificIndex, drafter, draftedPlayer);
	}
	
}
