package controllers;

import java.util.List;

import models.Notebook;
import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.NotebookRepository;
import views.html.index;

public class NotebookController extends Controller{

	private static NotebookRepository notebookRepository = Spring.getBeanOfType(NotebookRepository.class);
	
	public static Result list(){
		return ok(index.render((List<Notebook>)notebookRepository.findAll()));
	}
	
	public static Result create(String title){
		Notebook notebook = new Notebook();
		notebook.setTitle(title);
		
		notebookRepository.save(notebook);

		return ok();
	}
	
	public static Result delete(String id){
		notebookRepository.delete(id);
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
