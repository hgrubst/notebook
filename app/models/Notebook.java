package models;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.index.Indexed;

import play.modules.spring.Spring;
import repositories.NoteRepository;


@JsonIgnoreProperties("notes")
public class Notebook extends AbstractAuditable{

	private String id;

	private String title;

	@Indexed
	private String userEmail;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Note> getNotes() {
		return Spring.getBeanOfType(NoteRepository.class).findByNotebookId(id);
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
