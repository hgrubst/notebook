package service;

import models.User;

import org.junit.Assert;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import repositories.BaseRepositoryTest;

public class UserServiceTest extends BaseRepositoryTest {

	@Autowired
	UserService userService;

	@Test
	public void testCreateUser() throws InterruptedException {
		User user = userService.createUser("test@test.com", "test");

		Assert.assertNotNull(user.getId());
		Assert.assertNotSame("test", user.getPassword());
		Assert.assertTrue(BCrypt.checkpw("test", user.getPassword()));
	}

}
