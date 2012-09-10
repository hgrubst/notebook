package repositories;

import java.util.List;

import models.Notebook;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotebookRepository extends PagingAndSortingRepository<Notebook, String> {

	public List<Notebook> findByUserEmail(String userEmail);
	
	
}
