package models;



public class Note {

//	@Transient
//	private MarkdownService markdownService = new MarkdownSerivceImpl();
	
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

//	@Transient
//	public String getContentAsHtml(){
//		return markdownService.getHtml(getContent());
//	}
}
