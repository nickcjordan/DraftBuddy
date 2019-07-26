package com.falifa.draftbuddy.ui.logic;

import java.util.ArrayList;

import com.falifa.draftbuddy.ui.model.Draft;

public class RandomIndexGenerator {
	
	static ArrayList<Integer> percentages;
	
	static {
		percentages = buildPercentages();
	}

	public static int generate(int pickNumber, int roundNumber) {
		
		if (pickNumber < Integer.valueOf(System.getProperty("pickNumberToStartAIVariability"))) {
			return 0; // first X picks are set to standard ADP
		}
		
		int x = (int) Math.round(Math.random()*99);
		
		int changed = x;
		if (roundNumber < 2) {
			changed = (int) (x * (0.9));
		}
		
		int index = percentages.get(changed);
		return index;
	}
	
	private static ArrayList<Integer> buildPercentages() {
		int perc0 = Integer.parseInt(System.getProperty("percent0"));
		int perc1 = Integer.parseInt(System.getProperty("percent1"));
		int perc2 = Integer.parseInt(System.getProperty("percent2"));
		int perc3 = Integer.parseInt(System.getProperty("percent3"));
		int perc4 = Integer.parseInt(System.getProperty("percent4"));
		int perc5 = Integer.parseInt(System.getProperty("percent5"));
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


