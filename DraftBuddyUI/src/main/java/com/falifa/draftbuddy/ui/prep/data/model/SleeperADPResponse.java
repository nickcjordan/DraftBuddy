package com.falifa.draftbuddy.ui.prep.data.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleeperADPResponse {
	
	private List<SleeperADPItem> items;

	public List<SleeperADPItem> getItems() {
		return items;
	}

	public void setItems(List<SleeperADPItem> items) {
		this.items = items;
	}
	
	
	

}
