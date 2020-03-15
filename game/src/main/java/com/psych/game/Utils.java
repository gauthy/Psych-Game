package com.psych.game;

import javax.validation.constraints.NotNull;

import com.psych.game.config.SpringConfiguration;
import com.psych.game.model.EllenAnswer;
import com.psych.game.model.GameMode;
import com.psych.game.model.Question;
import com.psych.game.repository.EllenAnswerRepository;
import com.psych.game.repository.QuestionRepository;

public class Utils {
    
	private static QuestionRepository questionRepository;
	
    private static EllenAnswerRepository ellenAnswerRepository;
    
    static {
    	questionRepository=(QuestionRepository) SpringConfiguration.contextProvider().getApplicationContext().getBean("questionRepository");
        ellenAnswerRepository= (EllenAnswerRepository) SpringConfiguration.contextProvider().getApplicationContext().getBean("ellenAnswerRepository");
    }

	public static Question getRandomQuestion(@NotNull GameMode gameMode) {
		return questionRepository.getRandomQuestion(gameMode);
	}

	public static EllenAnswer getEllenAnswer(Question question) {
		return ellenAnswerRepository.getRandomEllenAnswer(question);
	}
    
	
}
