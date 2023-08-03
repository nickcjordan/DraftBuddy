package com.falifa.draftbuddy.ui.draft.controller;

import static com.falifa.draftbuddy.ui.constants.DataSourcePaths.TAGS_CUSTOM_PATH;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.falifa.draftbuddy.ui.constants.Tag;
import com.falifa.draftbuddy.ui.draft.DraftManager;
import com.falifa.draftbuddy.ui.draft.LogicHandler;
import com.falifa.draftbuddy.ui.draft.compare.AlphabetizedPlayerComparator;
import com.falifa.draftbuddy.ui.draft.data.DraftState;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.prep.PlayerCache;
import com.falifa.draftbuddy.ui.prep.api.PlayerNameMatcher;
import com.falifa.draftbuddy.ui.prep.data.ModelUpdater;
import com.falifa.draftbuddy.ui.prep.data.NFLTeamManager;
import com.falifa.draftbuddy.ui.prep.data.StrategyFileHandler;

@Controller
public class TagController {
	
	private static final Logger log = LoggerFactory.getLogger(TagController.class);
	
	private static final String DASHBOARD_PAGE = "pages/dashboardPage";
	
	@Autowired
	private DraftManager draftManager;
	
	@Autowired
	private ModelUpdater modelUpdater;
	
	@Autowired
	private StrategyFileHandler strategyHandler;
	
	@RequestMapping(value = "/tagPlayer")
    public String tagPlayer(@RequestParam(defaultValue="") String playerIdWithTag, Model model) {
		modelUpdater.updateCommonAttributes(model);
		String[] split = playerIdWithTag.split(",");
		String fantasyProsPlayerId = split[0];
		String tagName = split[1];
		Player player = NFLTeamManager.getPlayerById(draftManager.resolvePlayerId(fantasyProsPlayerId));
		Tag tag = Tag.valueOf(Tag.class, tagName);
		log.info("tagging player " + player.getPlayerName() + " with tag " + tag.toString());
		strategyHandler.addTagsToPlayer(player, tag.getTag());
		updateTagFileWithNewPlayerTag(player);
		PlayerCache.updatePlayerJsonFileWithCachedData();
		return DASHBOARD_PAGE;
    }
	
	@RequestMapping(value = "/untagPlayer")
    public String untagPlayer(@RequestParam(defaultValue="") String playerIdWithTag, Model model) {
		modelUpdater.updateCommonAttributes(model);
		String[] split = playerIdWithTag.split(",");
		Player player = NFLTeamManager.getPlayerById(draftManager.resolvePlayerId(split[0]));
		Tag tag = Tag.valueOf(Tag.class, split[1]);
		log.info("untagging player " + player.getPlayerName() + " with tag " + tag.toString());
		strategyHandler.removeTagsFromPlayer(player, tag.getTag());
		updateTagFileWithNewPlayerTag(player);
		PlayerCache.updatePlayerJsonFileWithCachedData();
		return DASHBOARD_PAGE;
    }
	
	private void updateTagFileWithNewPlayerTag(Player player) {
		try {
			TreeSet<Player> sorted = new TreeSet<Player>(new AlphabetizedPlayerComparator());
			for (List<String> split : strategyHandler.getSplitLinesFromFile(TAGS_CUSTOM_PATH, true)) {
				Player p = getPlayerFromActiveDraftById(split.get(2));
				if (p != null) {
					sorted.add(p);
				} else {
					log.error("ERROR cleaning up players :: Player not found = {}", split.get(0));
				}
			}
			if (!sorted.contains(player)) {
				sorted.add(player);
			}
			strategyHandler.updateTagFileWithCleanedUpResults(sorted);
		} catch (Exception e) {
			log.error("ERROR cleaning up tags", e);
		}
	}
	
	public Player getPlayerFromActiveDraftById(String id) {
		if (id != null) {
			if (NFLTeamManager.getPlayersById().containsKey(id)) {
				return NFLTeamManager.getPlayersById().get(id);
			}
			
		}
		return null;
	}

}
