package models;

import javax.persistence.ManyToOne;

public class Note {

//	@Transient
//	private MarkdownService markdownService = new MarkdownSerivceImpl();
	
	private Long id;

	private String content;

	private Integer position;
	
	@ManyToOne
	private Notebook notebook;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Notebook getNotebook() {
		return notebook;
	}

	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

//	@Transient
//	public String getContentAsHtml(){
//		return markdownService.getHtml(getContent());
//	}
}
