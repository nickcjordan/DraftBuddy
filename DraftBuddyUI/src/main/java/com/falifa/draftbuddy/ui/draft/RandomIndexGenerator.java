package com.falifa.draftbuddy.ui.draft;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RandomIndexGenerator {
	
	@Value( "${pickNumberToStartAIVariability}" )
	private int aiVariabilityPick;
	
	@Value( "${percent0}" )
	private int perc0;
	@Value( "${percent1}" )
	private int perc1;
	@Value( "${percent2}" )
	private int perc2;
	@Value( "${percent3}" )
	private int perc3;
	@Value( "${percent4}" )
	private int perc4;
	@Value( "${percent5}" )
	private int perc5;
	
	List<Integer> percentageList;
	
	public RandomIndexGenerator() {
		percentageList = buildPercentages();
	}
	
	public int generate(int pickNumber, int roundNumber) {
		if (CollectionUtils.isEmpty(percentageList)) {
			percentageList = buildPercentages();
		}
		if (pickNumber < aiVariabilityPick) {
			return 0; // first X picks are set to standard ADP
		}
		int x = (int) Math.round(Math.random()*99);
		int percent = percentageList.get(x);
		return percent;
	}
	
	private ArrayList<Integer> buildPercentages() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < perc0; i++)	{ list.add(0); }
		for (int i = 0; i < perc1; i++)	{ list.add(1); }
		for (int i = 0; i < perc2; i++)	{ list.add(2); }
		for (int i = 0; i < perc3; i++)	{ list.add(3); }
		for (int i = 0; i < perc4; i++)	{ list.add(4); }
		for (int i = 0; i < perc5; i++)	{ list.add(5); }
		return list;
	}
}


