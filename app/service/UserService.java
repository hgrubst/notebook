package service;

import models.User;

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
			if(user.getPassword().equals(password)){
				log.info("Sucessfully authenticated '{}'", email);
				return true;
			}
		}
		
		log.info("Authentication failed for '{}'", email);

		return false;
	}
	
	
}
