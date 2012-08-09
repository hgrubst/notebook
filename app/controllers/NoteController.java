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
	
	public static Result create(String notebookId){
		Notebook notebook = notebookRepository.findOne(notebookId);
		
		String markdown = request().body().asFormUrlEncoded().get("markdown")[0];
		
		Note note = new Note();
		note.setContent(markdown);
		noteRepository.save(note);

		notebook.addNote(note);
		notebookRepository.save(notebook);

		log.info("Sucessfully added note '{}' to notebook '{}'", note.getId(), notebook.getId());
		
		return ok();
	}
	
	public static Result update(String noteId, String notebookId){
		return ok();
	}
	
	public static Result delete(String noteId, String notebookId){
		return ok();		
	}
	
	public static Result html(String noteId, String notebookId){
		return ok(noteRepository.findOne(noteId).getContentAsHtml());
	}
}
