package com.falifa.draftbuddy.api.sleeper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.model.DraftType;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraft;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftPick;
import com.falifa.draftbuddy.api.sleeper.model.SleeperDraftState;
import com.falifa.draftbuddy.api.sleeper.model.SleeperUser;

@Component
public class SleeperAPI {
	
	private static final Logger log = LoggerFactory.getLogger(SleeperAPI.class);
	
	@Autowired
	private SleeperDelegate delegate;

	public List<SleeperDraftPick> getDraftPicks(String draftId) {
		return delegate.getDraftPicks(draftId);
	}
	

	public SleeperDraftState getDraftState(String draftId) {
		SleeperDraftState state = new SleeperDraftState();
		
		SleeperDraft draft = delegate.getDraft(draftId);
		state.setDraft(draft);
		
		if (draft.getLeagueId() != null) {
			state.setDrafters(getUsersForRealDraft(draft));
		} else {
			state.setDrafters(getUsersForMockDraft(draft));
		}
		
		
		return state;
	}


	private List<SleeperUser> getUsersForRealDraft(SleeperDraft draft) {
		List<SleeperUser> users = delegate.getUsersInLeague(draft.getLeagueId());
		if (draft.getDraftOrder() != null) {
			for (SleeperUser user : users) {
				String order = draft.getDraftOrder().get(user.getUserId());
				if (order != null) {
					user.setPickPosition(Integer.valueOf(order));
				}
			}
			users.sort(Comparator.comparing(SleeperUser::getPickPosition));
		}
		return users;
	}


	private List<SleeperUser> getUsersForMockDraft(SleeperDraft draft) {
		List<SleeperUser> users = new ArrayList<>();
		for (int i = 1; i <= draft.getSettings().getTeams(); i++) {
			String name = DraftType.SLEEPER.getOrder()[i-1];
			users.add(new SleeperUser(name, i));
		}
		for (String userId : draft.getDraftOrder().keySet()) {
			Integer pickPosition = Integer.valueOf(draft.getDraftOrder().get(userId));
			SleeperUser user = delegate.getUser(userId);
			user.setPickPosition(pickPosition);
			users.set(pickPosition - 1, user);
		}
		return users;
	}
	
}
