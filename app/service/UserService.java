package service;

import models.User;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.UserRepository;

@Service
public class UserService {

	Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;

	
	public boolean authenticate(String email, String password){
	
		User user = userRepository.findByEmail(email);
		
		if(user != null){
			try {
				if(BCrypt.checkpw(password, user.getPassword())){
					log.info("Sucessfully authenticated '{}'", email);
					return true;
				}
			} catch (IllegalArgumentException e) {
				log.warn("Password check failed for user '{}' : '{}'. Is the password stored in plain text?", email, e.getMessage());
			}
		}
		
		log.info("Authentication failed for '{}'", email);

		return false;
	}
	
	public User createUser(String email, String password){
		return userRepository.save(new User(email, BCrypt.hashpw(password, BCrypt.gensalt())));
	}
}
