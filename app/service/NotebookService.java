package service;

import models.Notebook;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.NoteRepository;
import repositories.NotebookRepository;

@Service
public class NotebookService {

	@Autowired
	private NotebookRepository notebookRepository;

	@Autowired
	private NoteRepository noteRepository;
	
	public void delete(String notebookId){
		Notebook notebook = notebookRepository.findOne(notebookId);
		
		if(CollectionUtils.isNotEmpty(notebook.getNotes())){
			noteRepository.delete(notebook.getNotes());
		}
		
		notebookRepository.delete(notebookId);
	}
	
}
