package com.psych.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psych.game.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
	

}
