package controllers;

import org.apache.commons.lang.StringUtils;

import play.*;
import play.modules.spring.Spring;
import play.mvc.*;
import play.mvc.Http.*;
import repositories.NotebookRepository;

import models.*;

public class Secured extends Security.Authenticator {

	private static NotebookRepository notebookRepository = Spring
			.getBeanOfType(NotebookRepository.class);

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("email");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Application.login());
	}

	// Access rights

	public static boolean isOwnerOf(String notebookId) {
		return StringUtils.equals(notebookRepository.findOne(notebookId)
				.getUser().getEmail(), Context.current().request().username());
	}

}