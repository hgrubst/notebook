package models;

import org.springframework.data.annotation.Transient;

import play.modules.spring.Spring;

import service.MarkdownService;



public class Note {

	@Transient
	private MarkdownService markdownService = Spring.getBeanOfType(MarkdownService.class);
	
	private String id;

	private String content;

	private Integer position;

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
		return markdownService.getHtml(getContent());
	}
}
