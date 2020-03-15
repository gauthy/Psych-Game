package com.psych.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.psych.game.model.GameMode;
import com.psych.game.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

	@Query(value="select * from questions where gameMode:=gameMode ORDER BY RAND() limit 1",nativeQuery=true)
	Question getRandomQuestion(GameMode gameMode);
}
