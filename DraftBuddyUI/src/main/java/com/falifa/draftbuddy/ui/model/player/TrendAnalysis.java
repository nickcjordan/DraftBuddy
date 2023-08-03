package com.falifa.draftbuddy.ui.model.player;

import org.springframework.stereotype.Component;

@Component
public class TrendAnalysis {
	
	public String blurb;
	public Integer sentimentValue;
	
	public TrendAnalysis() {}
	
	public TrendAnalysis(String blurb, Integer sentimentValue) {
		super();
		this.blurb = blurb;
		this.sentimentValue = sentimentValue;
	}
	
	public String getBlurb() {
		return blurb;
	}
	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}
	public Integer getSentimentValue() {
		return sentimentValue;
	}
	public void setSentimentValue(Integer sentimentValue) {
		this.sentimentValue = sentimentValue;
	}

	
}
