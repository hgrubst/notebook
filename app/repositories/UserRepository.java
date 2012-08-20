package repositories;

import java.util.List;

import models.User;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

	public User findByEmail(String email);
}
