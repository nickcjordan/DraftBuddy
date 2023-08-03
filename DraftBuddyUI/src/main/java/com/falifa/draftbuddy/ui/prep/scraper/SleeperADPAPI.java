package com.falifa.draftbuddy.ui.prep.scraper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.ui.prep.data.model.SleeperADP;
import com.falifa.draftbuddy.ui.prep.data.model.SleeperADPItem;
import com.falifa.draftbuddy.ui.prep.data.model.SleeperADPResponse;

@Component
public class SleeperADPAPI {
	
	@Autowired
	private SleeperADPDelegate delegate;

	public List<SleeperADP> getADP() {
		List<SleeperADP> adp = new ArrayList<SleeperADP>();
		
		try {
			SleeperADPResponse response = delegate.getADP();
			if (response != null) {
					
				for (SleeperADPItem x : response.getItems()) {
					String name = x.getPlayer().getUnformatted();
//					Double val = Double.parseDouble(x.getAdp()); // old structure from 2022
					Double val = x.getAdp().getUnformatted(); // new structure 2023
					SleeperADP sleeperADP = new SleeperADP(name, val);
					adp.add(sleeperADP);
				}
			}
		} catch (Exception e) {
			System.err.println("ERROR retrieving Sleeper ADP data :: skipping for now...");
			e.printStackTrace();
		}
		
		return adp;
	}
	
	

}
