package controllers;

import java.util.List;

import models.Note;
import models.Notebook;

import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.Json;
import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;
import repositories.NoteRepository;
import repositories.NotebookRepository;

@Security.Authenticated(Secured.class)
@With(ForceHttps.class)
public class NoteController extends Controller{

	static Logger log = LoggerFactory.getLogger(NoteController.class);
	
	private static NotebookRepository notebookRepository = Spring.getBeanOfType(NotebookRepository.class);
	private static NoteRepository noteRepository = Spring.getBeanOfType(NoteRepository.class);

	public static Result list(String notebookId){
		
		//TODO: check that the notebook belongs to logged in user
		
		List<Note> notes = notebookRepository.findOne(notebookId).getNotes();
		
		ObjectNode notesJson = Json.newObject();
		notesJson.put("notes", Json.toJson(notes));

		return ok(notesJson);
	}
	
	public static Result create(String notebookId){
		Notebook notebook = notebookRepository.findOne(notebookId);
		
		String markdown = request().body().asJson().get("content").asText();
		
		Note note = new Note();
		note.setContent(markdown);
		noteRepository.save(note);

		notebook.addNote(note);
		notebookRepository.save(notebook);

		log.info("Sucessfully added note '{}' to notebook '{}'", note.getId(), notebook.getId());

		ObjectNode response = Json.newObject();
		response.put("id", note.getId());
		response.put("contentAsHtml", note.getContentAsHtml());

		return ok(response);
	}
	
	public static Result update(String noteId, String notebookId){
		String markdown = request().body().asJson().get("content").asText();
		
		Note note = noteRepository.findOne(noteId);
		note.setContent(markdown);
		
		noteRepository.save(note);
		
		log.info("Sucessfully updated note '{}' with new content : '{}'", note.getId(), note.getContent());
		
		ObjectNode response = Json.newObject();
		response.put("contentAsHtml", note.getContentAsHtml());
		
		return ok(response);
	}
	
	public static Result delete(String noteId, String notebookId){
		noteRepository.delete(noteId);
		return ok();		
	}
	
	public static Result html(String noteId, String notebookId){
		return ok(noteRepository.findOne(noteId).getContentAsHtml());
	}
}
