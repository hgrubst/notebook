package controllers;

import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.NotebookRepository;

public class NoteController extends Controller{

	private static NotebookRepository notebookRepository = Spring.getBeanOfType(NotebookRepository.class);

	public static Result list(String notebookId){
		return ok(views.html.notes.list.render(notebookRepository.findOne(notebookId).getNotes()));
	}
}
