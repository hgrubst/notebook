package service;

import models.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.NoteRepository;

@Service
public class NoteService {

	@Autowired
	NoteRepository noteRepository;
	
	public Note createNote(String notebookId, String content){
		//ensure notebook belongs to current user
		
		//ensure content is properly escaped here
		
		Note note = new Note();
		note.setContent(content);
		note.setNotebookId(notebookId);
		
		noteRepository.save(note);
		
		return note;
	}

	public Note updateContent(String noteId, String content){
		
		//ensure content is properly escaped here
		Note note = noteRepository.findOne(noteId);
		note.setContent(content);
		noteRepository.save(note);
		
		return note;
	}

}
