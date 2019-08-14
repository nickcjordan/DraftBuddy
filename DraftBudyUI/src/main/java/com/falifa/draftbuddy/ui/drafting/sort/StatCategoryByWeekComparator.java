package com.falifa.draftbuddy.ui.drafting.sort;

import java.util.Comparator;

import com.falifa.draftbuddy.ui.model.player.Player;
import com.falifa.draftbuddy.ui.model.player.stats.StatisticCategory;

public class StatCategoryByWeekComparator implements Comparator<String> {

	public int compare(String week1, String week2){
		return Integer.valueOf(week1).compareTo(Integer.valueOf(week2));
	}

}
