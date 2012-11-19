package service;

import java.util.List;

import models.Note;
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

	public Notebook createNotebook(String title, String userEmail) {
		Notebook notebook = new Notebook();
		notebook.setTitle(title);
		notebook.setUserEmail(userEmail);

		notebookRepository.save(notebook);

		return notebook;
	}

	public Notebook updateTitle(String notebookId, String title) {
		//ensure notebook belongs to current user
		
		Notebook notebook = notebookRepository.findOne(notebookId);
		notebook.setTitle(title);
		notebookRepository.save(notebook);

		return notebook;
	}

	public void delete(String notebookId) {
		List<Note> notes = noteRepository.findByNotebookId(notebookId);
		if (CollectionUtils.isNotEmpty(notes)) {
			noteRepository.delete(notes);
		}

		notebookRepository.delete(notebookId);
	}

}
