package com.falifa.draftbuddy.ui.model.player;

import java.util.ArrayList;
import java.util.List;

import com.falifa.draftbuddy.ui.constants.Position;

public class PositionalOverview {

	Position position;
	List<Integer> remainingCounts;
	Integer total;

	public PositionalOverview() { 
		initCounts();
	}
	
	public PositionalOverview(Position position) {
		this.position = position;
		initCounts();
	}
	
	private void initCounts() {
		this.remainingCounts = new ArrayList<Integer>();
		this.remainingCounts.add(0);
		this.remainingCounts.add(0);
		this.remainingCounts.add(0);
		this.remainingCounts.add(0);
		this.remainingCounts.add(0);
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<Integer> getRemainingCounts() {
		return remainingCounts;
	}

	public void setRemainingCounts(List<Integer> remainingCounts) {
		this.remainingCounts = remainingCounts;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
