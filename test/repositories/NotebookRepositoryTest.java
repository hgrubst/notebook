package repositories;

import java.util.List;

import models.Notebook;
import models.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:conf/application-context.xml","file:conf/application-context-test.xml"})
public class NotebookRepositoryTest {

	@Autowired
	NotebookRepository notebookRepository;

	@Autowired
	UserRepository userRepository;
	
	private Notebook notebook;
	
	@Before
	public void setup(){
		notebook = new Notebook();
		notebook.setTitle("testNotebook1");
	}
	
	@Test
	public void testCrud(){
		//create
		Assert.assertNull(notebook.getId());
		notebook = notebookRepository.save(notebook);
		Assert.assertNotNull(notebook.getId());
		
		//update/retrieve
		String changedTitle = "testNotebook1Changed"; 
		notebook.setTitle(changedTitle);
		notebookRepository.save(notebook);
		notebook = notebookRepository.findOne(notebook.getId());
		Assert.assertEquals(changedTitle, notebook.getTitle());
		
		//delete
		notebookRepository.delete(notebook);
		Assert.assertNull(notebookRepository.findOne(notebook.getId()));
	}
	
	@Test
	public void testFindByUserEmail(){
		String email = "test@test.com";

		int nbNotebooksBefore = notebookRepository.findByUserEmail(email).size();
		
		notebook.setUserEmail(email);
		notebookRepository.save(notebook);
		
		int nbNotebooksAfter = notebookRepository.findByUserEmail(email).size();
		
		Assert.assertTrue(nbNotebooksAfter == nbNotebooksBefore+1);
	}
}
