package com.falifa.draftbuddy.ui.model.player;

import java.util.ArrayList;
import java.util.List;

public class NotesMetadata {
	
	private List<PlayerNote> notes;
	
	public NotesMetadata() {
		notes = new ArrayList<PlayerNote>();
	}
	
	public void addNote(PlayerNote note) {
		 notes.add(note);
	}

	public List<PlayerNote> getNotes() {
		return notes;
	}
	
}
