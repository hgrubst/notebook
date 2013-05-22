package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Routes;
import play.data.Form;
import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import service.UserService;
import views.html.login;

@With(ForceHttps.class)
public class Application extends Controller {

	static Logger log = LoggerFactory.getLogger(Application.class);
	
	private static UserService userService = Spring
			.getBeanOfType(UserService.class);

	// -- Authentication

	public static class Login {

		public String email;
		public String password;

		public String validate() {
			if (!userService.authenticate(email, password)) {
				return "Invalid user or password";
			}
			return null;
		}

	}

	/**
	 * Login page.
	 */
	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	/**
	 * Handle login form submission.
	 */
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session("email", loginForm.get().email);
			return redirect(routes.NotebookController.index());
		}
	}

	/**
	 * Logout and clean the session.
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Application.login());
	}

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
				controllers.routes.javascript.NotebookController.list(),
				controllers.routes.javascript.NotebookController.create(),
				controllers.routes.javascript.NotebookController.delete(),
				controllers.routes.javascript.NoteController.list(),
				controllers.routes.javascript.NoteController.create(),
				controllers.routes.javascript.NoteController.update(),
				controllers.routes.javascript.NoteController.html(),
				controllers.routes.javascript.NoteController.delete()));
	}

}