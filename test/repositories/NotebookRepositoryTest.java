package repositories;

import models.Notebook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NotebookRepositoryTest extends BaseRepositoryTest{

	@Autowired
	NotebookRepository notebookRepository;

	@Autowired
	UserRepository userRepository;
	
	private Notebook notebook;
	
	@Before
	public void setup(){
		mongoTemplate.dropCollection(Notebook.class);
		
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
