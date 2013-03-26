package controllers;

import java.util.List;

import models.Notebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.Json;
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
		return ok(index.render());
	}

	public static Result list() {
		List<Notebook> notebooks = (List<Notebook>) notebookRepository
				.findByUserEmail(request().username());

		if(RequestUtil.isNotAjaxRequest(request())){
			redirect(routes.NotebookController.index());
		}
		
		log.debug("Found {} notebooks for user  {}", notebooks.size(),
				request().username());


		return ok(Json.toJson(notebooks));
	}

	public static Result create(String title) {
		Notebook notebook = notebookService.createNotebook(title, request().username());
		
		return ok(Json.toJson(notebook));
	}

	public static Result delete(String id) {
		notebookService.delete(id);
		return ok();
	}

	public static Result update(String id) {
		String title = request().body().asFormUrlEncoded().get("title")[0];

		notebookService.updateTitle(id, title);

		return ok();
	}
	
	public static Result show(String id){
		return index();
	}
}
