package com.falifa.draftbuddy.ui.tracker;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import com.falifa.draftbuddy.ui.constants.DraftType;
import com.falifa.draftbuddy.ui.data.DraftState;
import com.falifa.draftbuddy.ui.data.ModelUpdater;
import com.falifa.draftbuddy.ui.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.drafting.DraftManager;
import com.falifa.draftbuddy.ui.model.Draft;
import com.falifa.draftbuddy.ui.model.player.Player;

@Component
public class MockDraftTrackerRunner {
	
	private static final Logger log = LoggerFactory.getLogger(MockDraftTrackerRunner.class);
	
	@Autowired
	private DraftState draftState;
	
	@Autowired
	protected NFLTeamManager nflTeams;
	
	@Autowired
	private ModelUpdater modelUpdater;
	
	@Autowired
	private DraftManager draftManager;
	
	private ExecutorService es;
	
	public void trackMockDraft(Map<String, String> pickMap, Map<String, String> playerIdToNameMap, Model model) {
		initializeTracking(model);
		
//		MockDraftAsynchTracker tracker = new MockDraftAsynchTracker(pickMap, playerIdToNameMap);
//		es = Executors.newCachedThreadPool();
//		try {
//			es.execute(tracker);
//		} catch (Exception e) {
//			log.error("ERROR trying to run tracker thread", e);
//		}
		
	}
	
	public void stopTracker() {
		shutExecutorDown(es);
	}
	
	private void shutExecutorDown(ExecutorService es) {
		es.shutdown();
		try {
			es.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("Error shutting down executor service :: error message: {}", e.getMessage());
		}
	}

	private void initializeTracking(Model model) {
		if (CollectionUtils.isEmpty(draftState.getDraftPicks())) {
			nflTeams.initializeNFL();
			draftState.initializeDraft();
		}
		draftState.mockDraftMode = true;
		draftState.draftType = DraftType.TRACKED_DRAFT;
		log.info("Tracking");
		draftState.draft = new Draft(draftState.draftType.getOrder());
		draftState.currentDrafter = draftState.draft.getDrafters().get(0);
		draftManager.setOptimizedDrafter(draftState.draft);
		draftState.initializeDraftersDraftPickIndexList();
		modelUpdater.updateCommonAttributes(model);
		draftManager.clearUndoToStack();
	}

	public boolean makePick(String name) {
		Player player = nflTeams.getPlayerFromName(name);
		log.info("Tracker pick :: player={}", player.getPlayerName());
		draftManager.doBaseDraft(player);
		return true;
	}

}
