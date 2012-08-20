package controllers;

import java.util.List;

import models.Notebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.NotebookRepository;
import service.NotebookService;
import views.html.*;

@Security.Authenticated(Secured.class)
public class NotebookController extends Controller{

	private static NotebookRepository notebookRepository = Spring.getBeanOfType(NotebookRepository.class);
	private static NotebookService notebookService = Spring.getBeanOfType(NotebookService.class);
	
	static Logger log = LoggerFactory.getLogger(NotebookController.class);
	
	public static Result index() {
		return ok(index.render((List<Notebook>) notebookRepository.findAll()));
	}
	
	public static Result list(){
		List<Notebook> notebooks = (List<Notebook>)notebookRepository.findAll();
		
		for (Notebook notebook : notebooks) {
			log.debug("notebook {} has {} notes", notebook.getId(), notebook.getNotes().size());
		}
		
		return ok(index.render(notebooks));
	}
	
	public static Result create(String title){
		Notebook notebook = new Notebook();
		notebook.setTitle(title);
		
		notebookRepository.save(notebook);

		return ok(notebook.getId());
	}
	
	public static Result delete(String id){
		notebookService.delete(id);
		return ok();
	}

	public static Result update(String id){
		String title = "figure out a way to get the title";
		
		Notebook notebook = notebookRepository.findOne(id);
		notebook.setTitle(title);
		
		notebookRepository.save(notebook);

		return ok();
	}
}
