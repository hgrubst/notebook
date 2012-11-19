package repositories;

import models.Note;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NoteRepositoryTest extends BaseRepositoryTest{

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	NotebookRepository notebookRepository;

	private Note note;
	
	@Before
	public void setup(){
		mongoTemplate.dropCollection(Note.class);
		
		note = new Note();
		note.setContent("test content");
	}
	
	@Test
	public void testCrud(){
		//create
		Assert.assertNull(note.getId());
		note = noteRepository.save(note);
		Assert.assertNotNull(note.getId());
		
		//update/retrieve
		String changedContent = "test content changed"; 
		note.setContent(changedContent);
		noteRepository.save(note);
		note = noteRepository.findOne(note.getId());
		Assert.assertEquals(changedContent, note.getContent());
		
		//delete
		noteRepository.delete(note);
		Assert.assertNull(noteRepository.findOne(note.getId()));
	}
	
	@Test
	public void testFindByNotebookId(){
		String notebookId = "1234";

		int nbNotesBefore = noteRepository.findByNotebookId(notebookId).size();
		
		note.setNotebookId(notebookId);
		noteRepository.save(note);
		
		int nbNotesAfter = noteRepository.findByNotebookId(notebookId).size();
		
		Assert.assertTrue(nbNotesAfter == nbNotesBefore+1);
	}
}
