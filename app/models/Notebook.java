package models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;


public class Notebook {

	private String id;

	private String title;

	@DBRef
	private Set<Note> notes;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	public void addNote(Note note){
		if(notes == null){
			notes = new HashSet<Note>();
		}
		notes.add(note);
	}
}
