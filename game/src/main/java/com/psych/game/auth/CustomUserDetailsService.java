package com.psych.game.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.psych.game.exceptions.NoSuchUserException;
import com.psych.game.model.User;
import com.psych.game.repository.UserRepository;

import lombok.SneakyThrows;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    
		Optional<User> user=userRepository.findByEmail(email);
		if(!user.isPresent()) {
				try {
					throw new NoSuchUserException("User Not found with email " + email);
				} catch (NoSuchUserException e) {
					e.printStackTrace();
				}
		}
		return new CustomerDetailsUser(user.get());
	}

}
