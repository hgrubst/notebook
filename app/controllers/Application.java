package controllers;

import java.util.List;

import models.Notebook;
import play.Routes;
import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.NotebookRepository;
import views.html.index;

public class Application extends Controller {

	private static NotebookRepository notebookRepository = Spring
			.getBeanOfType(NotebookRepository.class);

	public static Result index() {
		return ok(index.render((List<Notebook>) notebookRepository.findAll()));
	}

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
				controllers.routes.javascript.NotebookController.create(),
				controllers.routes.javascript.NotebookController.delete(),
				controllers.routes.javascript.NoteController.create(),
				controllers.routes.javascript.NoteController.html(),
				controllers.routes.javascript.NoteController.list()));
	}

}