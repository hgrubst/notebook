package repositories;

import java.util.List;

import models.Note;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, String> {

	public List<Note> findByNotebookId(String notebookId);
	
}
