package repositories;

import models.Note;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, String> {

	
	
}
