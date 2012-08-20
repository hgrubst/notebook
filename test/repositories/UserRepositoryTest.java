package repositories;

import models.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "conf/application-context.xml")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	public void test() {

		String email = "test@notello.com";
		String password = "test";

		userRepository.save(new User(email, password));

		User user = userRepository.findByEmail(email);

		Assert.assertEquals(email, user.getEmail());
	}

}
