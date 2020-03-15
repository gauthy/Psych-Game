package com.psych.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psych.game.model.EllenAnswer;
import com.psych.game.model.Question;

@Repository
public interface EllenAnswerRepository extends JpaRepository<EllenAnswer, Long>{
  
	
	@Query(value="select * from questions where gameMode:=gameMode ORDER BY RAND() limit 1",nativeQuery=true)
	EllenAnswer getRandomEllenAnswer(Question question);
}
