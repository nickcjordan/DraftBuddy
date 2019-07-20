package com.falifa.draftbuddy.ui.logic;

import java.util.ArrayList;

import com.falifa.draftbuddy.ui.Log;
import com.falifa.draftbuddy.ui.controller.BaseController;
import com.falifa.draftbuddy.ui.model.Draft;

public class RandomIndexGenerator {
	
	static ArrayList<Integer> percentages;
	
	static {
		percentages = buildPercentages();
	}

	public static int generate() {
		
		if (BaseController.pickNumber < Integer.valueOf(BaseController.getProperties().getProperty("pickNumberToStartAIVariability"))) {
			return 0; // first X picks are set to standard ADP
		}
		
		int x = (int) Math.round(Math.random()*99);
		
		int changed = x;
		if (BaseController.roundNum < 2) {
			changed = (int) (x * (0.9));
		}
		
		int index = percentages.get(changed);
		Log.deb("Generated index --> " + x + " --> " + changed + " :: " + index);
		return index;
	}
	
	private static ArrayList<Integer> buildPercentages() {
		int perc0 = Integer.parseInt(BaseController.getProperties().getProperty("percent0"));
		int perc1 = Integer.parseInt(BaseController.getProperties().getProperty("percent1"));
		int perc2 = Integer.parseInt(BaseController.getProperties().getProperty("percent2"));
		int perc3 = Integer.parseInt(BaseController.getProperties().getProperty("percent3"));
		int perc4 = Integer.parseInt(BaseController.getProperties().getProperty("percent4"));
		int perc5 = Integer.parseInt(BaseController.getProperties().getProperty("percent5"));
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


