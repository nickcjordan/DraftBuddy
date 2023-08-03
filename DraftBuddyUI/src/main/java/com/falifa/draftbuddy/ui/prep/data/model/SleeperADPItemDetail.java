package com.falifa.draftbuddy.ui.prep.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleeperADPItemDetail {
	
	@JsonProperty("formatted")
	private String formatted;
	
	@JsonProperty("unformatted")
	private double unformatted;

	public String getFormatted() {
		return formatted;
	}

	public void setFormatted(String formatted) {
		this.formatted = formatted;
	}

	public double getUnformatted() {
		return unformatted;
	}

	public void setUnformatted(double unformatted) {
		this.unformatted = unformatted;
	}
	
	

}
