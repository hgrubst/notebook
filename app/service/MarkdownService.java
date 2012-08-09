package service;

import org.springframework.stereotype.Service;

import com.petebevin.markdown.MarkdownProcessor;

@Service
public class MarkdownService {

	MarkdownProcessor markdownProcessor = new MarkdownProcessor();
	
	public String getHtml(String markdown){
		return markdownProcessor.markdown(markdown);
	}
	
	
	
}
