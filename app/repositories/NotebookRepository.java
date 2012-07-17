package repositories;

import models.Notebook;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotebookRepository extends PagingAndSortingRepository<Notebook, String> {

	
	
}
