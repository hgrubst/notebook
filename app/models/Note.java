package models;

import play.modules.spring.Spring;
import repositories.NotebookRepository;
import service.MarkdownService;



public class Note extends AbstractAuditable{

	private String id;

	private String content;

	private Integer position;

	private String notebookId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getContentAsHtml(){
		return Spring.getBeanOfType(MarkdownService.class).getHtml(getContent());
	}

	public String getNotebookId() {
		return notebookId;
	}

	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}
	
	public Notebook getNotebook(){
		return Spring.getBeanOfType(NotebookRepository.class).findOne(notebookId);
	}
}
