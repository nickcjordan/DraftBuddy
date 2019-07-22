package com.falifa.draftbuddy.ui.model.player;

public class PlayerNote {
	
	private String timestamp;
	private String text;
	private String source;
	
	public PlayerNote(String timestamp, String text, String source) {
		this.timestamp = timestamp;
		this.text = text;
		this.source = source;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

}
