package controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Routes;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import play.modules.spring.Spring;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import repositories.UserRepository;
import service.UserService;
import views.html.login;
import views.html.signup;

@With(ForceHttps.class)
public class Application extends Controller {

	static Logger log = LoggerFactory.getLogger(Application.class);

	private static UserService userService = Spring
			.getBeanOfType(UserService.class);
	private static UserRepository userRepository = Spring
			.getBeanOfType(UserRepository.class);

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

	public static class Signup {
		@Required(message="Email is required")
		public String email;

		@Required(message="Password is required")
		public String password;

		@Required(message="Password confirmation is required")
		public String confirmPassword;

		public List<ValidationError> validate() {
			
			log.debug("validating signup form email-> {}, password-> {}, confirmPassword -> {}: ", new String[]{email, password, confirmPassword});
			
			List<ValidationError> errors = new ArrayList<ValidationError>();

			if (userRepository.findByEmail(email) != null) {
				errors.add(new ValidationError("", "User already exists, choose another email", null));
			}

			if (!password.equals(confirmPassword)) {
				errors.add(new ValidationError("", "Passwords do not match", null));
			}

			return errors.isEmpty() ? null : errors;
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

	public static Result signup() {
		return ok(signup.render(form(Signup.class)));
	}

	public static Result createAccount() {
		log.debug("Entering createAccount");
		Form<Signup> signupForm = form(Signup.class).bindFromRequest();
		if (signupForm.hasErrors()) {
			log.debug("signupform has errors : {} ", signupForm.errorsAsJson());
			return badRequest(signup.render(signupForm));
		} else {
			userService.createUser(signupForm.get().email, signupForm.get().password);
			session("email", signupForm.get().email);
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