package controllers;

import java.util.List;

import models.Notebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;
import repositories.NotebookRepository;
import service.NotebookService;
import views.html.index;

@Security.Authenticated(Secured.class)
@With(ForceHttps.class)
public class NotebookController extends Controller {

	private static NotebookRepository notebookRepository = Spring
			.getBeanOfType(NotebookRepository.class);
	private static NotebookService notebookService = Spring
			.getBeanOfType(NotebookService.class);

	static Logger log = LoggerFactory.getLogger(NotebookController.class);

	public static Result index() {
		return list();
	}

	public static Result list() {
		List<Notebook> notebooks = (List<Notebook>) notebookRepository
				.findByUserEmail(request().username());

		log.debug("Found {} notebooks for user  {}", notebooks.size(),
				request().username());

		return ok(index.render(notebooks));
	}

	public static Result create(String title) {
		Notebook notebook = new Notebook();
		notebook.setTitle(title);
		notebook.setUserEmail(request().username());

		notebookRepository.save(notebook);

		return ok(notebook.getId());
	}

	public static Result delete(String id) {
		notebookService.delete(id);
		return ok();
	}

	public static Result update(String id) {
		String title = "figure out a way to get the title";

		Notebook notebook = notebookRepository.findOne(id);
		notebook.setTitle(title);

		notebookRepository.save(notebook);

		return ok();
	}
}
