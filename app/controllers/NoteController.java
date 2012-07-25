package controllers;

import models.Note;
import models.Notebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.NoteRepository;
import repositories.NotebookRepository;

public class NoteController extends Controller{

	static Logger log = LoggerFactory.getLogger(NoteController.class);
	
	private static NotebookRepository notebookRepository = Spring.getBeanOfType(NotebookRepository.class);
	private static NoteRepository noteRepository = Spring.getBeanOfType(NoteRepository.class);

	public static Result list(String notebookId){
		return ok(views.html.notes.list.render(notebookRepository.findOne(notebookId).getNotes()));
	}
	
	public static Result create(String id, String noteContent){
		Notebook notebook = notebookRepository.findOne(id);
		
		Note note = new Note();
		note.setContent(noteContent);
		noteRepository.save(note);

		notebook.addNote(note);
		notebookRepository.save(notebook);

		log.info("Sucessfully added note '{}' to notebook '{}'", note.getId(), notebook.getId());
		
		return ok();
	}
}
