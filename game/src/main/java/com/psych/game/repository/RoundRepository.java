package com.psych.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psych.game.model.Round;

public interface RoundRepository extends JpaRepository<Round, Long>{
   
}
