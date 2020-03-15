package com.psych.game.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psych.game.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	Optional<Player> findByEmail(String email);
}
