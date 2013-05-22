package controllers;

import java.util.List;

import models.Note;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.Json;
import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;
import repositories.NoteRepository;
import service.NoteService;

@Security.Authenticated(Secured.class)
@With(ForceHttps.class)
public class NoteController extends Controller{

	static Logger log = LoggerFactory.getLogger(NoteController.class);
	
	private static NoteRepository noteRepository = Spring.getBeanOfType(NoteRepository.class);
	private static NoteService noteService = Spring.getBeanOfType(NoteService.class);

	public static Result list(String notebookId){
		
		//TODO: check that the notebook belongs to logged in user
		
		List<Note> notes = noteRepository.findByNotebookId(notebookId);
		
		return ok(Json.toJson(notes));
	}
	
	public static Result create(String notebookId){
		String markdown = request().body().asFormUrlEncoded().get("content")[0];
		
		Note note = noteService.createNote(notebookId, markdown);
		
		log.info("Sucessfully added note '{}' to notebook '{}'", note.getId(), notebookId);

		return ok(Json.toJson(note));
	}
	
	public static Result update(String noteId, String notebookId){
		String markdown = request().body().asFormUrlEncoded().get("content")[0];
		
		Note note = noteService.updateContent(noteId, markdown);
		
		log.info("Sucessfully updated note '{}' with new content : '{}'", note.getId(), note.getContent());
		
		return ok(Json.toJson(note));
	}
	
	public static Result delete(String noteId, String notebookId){
		noteRepository.delete(noteId);
		log.debug("Sucessfully deleted note '{}'", noteId);
		return ok();		
	}
	
	public static Result html(String noteId, String notebookId){
		return ok(noteRepository.findOne(noteId).getContentAsHtml());
	}
}
