package com.psych.game.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psych.game.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
	Optional<User> findByEmail(String email);
}
