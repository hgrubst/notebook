package service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class MarkdownServiceTest {

	MarkdownService markdownService;

	String md = "**bold text**";

	@Before
	public void setup() {
		markdownService = new MarkdownService();
	}

	@Test
	public void testGetHtml() {
		String html = null;

		html = markdownService.getHtml(md);

		Assert.assertTrue(html.contains("<strong>bold text</strong>"));
	}

}
