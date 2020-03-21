package com.psych.game.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psych.game.model.GameMode;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, Long> {

	 Optional<GameMode> findByName(String gameMode);
}
