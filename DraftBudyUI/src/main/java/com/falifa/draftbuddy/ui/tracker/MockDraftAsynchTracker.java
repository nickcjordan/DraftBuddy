package com.falifa.draftbuddy.ui.tracker;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jaunt.ResponseException;
import com.jaunt.UserAgent;

public class MockDraftAsynchTracker implements Runnable {
	
	
	private static final Logger log = LoggerFactory.getLogger(MockDraftAsynchTracker.class);


	private static final String BASE_DRAFT_PICK_URL = "http://localhost:8080/start?appRunType=type";

	
	private Map<String, String> pickMap;
	private Map<String, String> playerIdToNameMap;
	private UserAgent userAgent;
	
	public MockDraftAsynchTracker(Map<String, String> pickMap, Map<String, String> playerIdToNameMap) {
		this.pickMap = pickMap;
		this.playerIdToNameMap = playerIdToNameMap;
	}

	@Override
	public void run() {
		for (int pickNum = 1; pickNum <= pickMap.size(); pickNum++) {
			if (pickMap.containsKey(String.valueOf(pickNum))) {
				String id = pickMap.get(String.valueOf(pickNum));
				String name = playerIdToNameMap.get(id);
				log.info("Tracking player pick={}", name);
				String url = BASE_DRAFT_PICK_URL + name;
				try {
					userAgent.sendGET(url);
				} catch (ResponseException e) {
					log.error("ERROR inside asynch mock draft tracker :: error calling url=" + url, e);
				}
			} else {
				log.error("ERROR pickMap does not have entry for '{}'");
			}
		}
		
		
		int count = 0;
		
		while (count < 30) {
			
			log.info("Count={}", count);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace(); // TODO
			}
			count++;
		}
		
	}

}
