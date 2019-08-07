package com.falifa.draftbuddy.ui.scraper.extractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.data.PlayerPopulator;
import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticCategory;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticValue;
import com.falifa.draftbuddy.ui.scraper.JsonDataFileManager;
import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;

@Component
public class PositionalProjectionsExtractor {
	
	
	private static final Logger log = LoggerFactory.getLogger(PositionalProjectionsExtractor.class);

	
	@Autowired
	private JsonDataFileManager playerManager;
	
	@Autowired
	private PlayerPopulator playerPopulator;

	public boolean extractPositionalProjectionDataFromElement(Element table) throws NotFound {
		List<StatisticCategory> categories = buildStatisticsCategories(table.findFirst("<thead>").findEach("<tr>"));
		return handlePlayerRows(table.findFirst("<tbody>").findEach("<tr>").iterator(), categories);
	}

	private boolean handlePlayerRows(Iterator<Element> iterator, List<StatisticCategory> categories) {
		boolean success = true;
		while(iterator.hasNext()) {
				success &= handlePlayerRow(iterator.next(), categories);
		}
		return success;
	}

	private boolean handlePlayerRow(Element playerRow, List<StatisticCategory> categories) {
		String fantasyProsId;
		try {
			fantasyProsId = playerRow.getAt("class").split("-")[2];
			Player p = playerManager.getPlayerFromTemporaryStorage(fantasyProsId);
			if (p != null) {
				Iterator<Element> statsIterator = playerRow.findEach("<td>").iterator();
				for (StatisticCategory category : categories) {
					p.getProjectedRawStatsDetails().addStatCategory(buildPlayerStatCategory(category, statsIterator));
				}
				playerPopulator.populateProjectedStatsMap(p);
				playerPopulator.populatePlayerProjectedTotalsFields(p);
				return true;
			}
		} catch (Exception e) {
			log.error("ERROR adding player stats :: " + playerRow.toString());
		}
		return false;
	}

	private StatisticCategory buildPlayerStatCategory(StatisticCategory category, Iterator<Element> statsIterator) {
		StatisticCategory newCat = new StatisticCategory();
		newCat.setName(category.getName());
		for (StatisticValue val : category.getColumns()) {
			if (statsIterator.hasNext()) {
				Element dataElement = statsIterator.next();
				if (!val.getName().equals("SKIP")) {
					newCat.addColumn(val.getName(), dataElement.getTextContent());
				}
			}
		}
		return newCat;
	}

	private List<StatisticCategory> buildStatisticsCategories(Elements tableHeaderRows) throws NotFound {
		List<StatisticCategory> categories = null;
		try {
			categories = buildStatisticCategoriesList(tableHeaderRows.getElement(0));
			addColumnsToCategories(categories, tableHeaderRows.getElement(1).findEach("<th>").iterator());
		} catch (NotFound e) {
			categories = buildSingleCategoryList(tableHeaderRows);
		}
		return categories;
	}

	private List<StatisticCategory> buildSingleCategoryList(Elements tableHeaderRow) throws NotFound {
		List<StatisticCategory> categories = new ArrayList<StatisticCategory>();
		Iterator<Element> iter = tableHeaderRow.findEach("<th>").iterator();
		iter.next(); // move past id
		StatisticCategory category = new StatisticCategory();
		category.setName("ALL");
		category.addColumn("SKIP", "");
		while (iter.hasNext()) {
			category.addColumn(iter.next().findFirst("small").getTextContent(), "");
		}
		categories.add(category);
		return categories;
	}

	private boolean addColumnsToCategories(List<StatisticCategory> categories, Iterator<Element> iterator) {
		boolean success = true;
		for (StatisticCategory category : categories) {
			int count = category.getColspan();
			while (count-- > 0) {
				try {
					if (iterator.hasNext()) {
						Element row = iterator.next();
						if (!category.getName().equals("SKIP")) {
								category.addColumn(row.findFirst("small").getTextContent(), "");
						} else {
							category.addColumn("SKIP", "");
						}
					}
				} catch (Exception e) {
					log.error("ERROR adding statistic column to StatisticCategory", e);
					success = false;
				}
			}
		}
		return success;
	}

	private List<StatisticCategory> buildStatisticCategoriesList(Element row) throws NotFound {
		List<StatisticCategory> categories = new ArrayList<StatisticCategory>();
		Iterator<Element> categoryIterator = row.findEach("<td>").iterator();
		while (categoryIterator.hasNext()) {
			Element cat = categoryIterator.next();
			if (!cat.hasAttribute("colspan")) {
				categories.add(buildCategorySkip()); // add mock category to be skipped
			} else {
				Optional.ofNullable(buildCategory(cat)).map(c -> categories.add(c));
			}
		}
		return categories;
	}

	private StatisticCategory buildCategorySkip() {
		StatisticCategory category = new StatisticCategory();
		category.setColspan(1);
		category.setName("SKIP");
		return category;
	}

	private StatisticCategory buildCategory(Element row) {
		StatisticCategory category = new StatisticCategory();
		try {
			category.setColspan(Integer.valueOf(row.getAt("colspan")));
			category.setName(row.findFirst("<b>").getTextContent());
		} catch (NumberFormatException | NotFound e) {
			log.error("Error building StatisticCategory :: " + row.toString(), e);
		}
		return category;
	}
	
}
