package com.falifa.draftbuddy.ui.prep.scraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.falifa.draftbuddy.api.sleeper.SleeperDelegate;
import com.falifa.draftbuddy.ui.prep.data.model.SleeperADPResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Component
public class SleeperADPDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(SleeperDelegate.class);
	private static final String adpUrl = "https://www.ftnfantasy.com/api/datatables/table-contents/9660bf59-8d6e-4315-ae3d-5307aa1a4b0b/ajax?page=1&perPage=300&sort=Redraft%20PPR%20ADP&direction=0&col_filter_data=%7B%7D&data_filter_data=%7B%7D&injected_filter_data=%7B%7D";
	
	public SleeperADPResponse getADP() {
		try {
			Unirest.setTimeouts(0, 0);
			HttpResponse<String> rr = Unirest.get(adpUrl)
			  .asString();
			SleeperADPResponse body = new ObjectMapper().readValue(rr.getBody(), SleeperADPResponse.class);
			
			return body;
		} catch (Exception e) {
			log.error("ERROR :: " + e.getLocalizedMessage() + " : "+ e.getMessage() + " :: Failed to get Sleeper ADP from url " + adpUrl);
			return null;
		}
	}
	
}
