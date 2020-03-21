package com.psych.game.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "questions")
public class Question extends Auditable {
	@NotNull
	private String question;

	@NotNull
	private String correctAnswer;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
	@JsonManagedReference
	private Set<EllenAnswer> ellenAnswers = new HashSet<>();

	@JsonIdentityReference
	@NotNull
	private GameMode gameMode;

	public Question() {
	}

	public Question(@NotNull String question, @NotNull String correctAnswer, @NotNull GameMode gameMode) {
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.gameMode = gameMode;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Set<EllenAnswer> getEllenAnswers() {
		return ellenAnswers;
	}

	public void setEllenAnswers(Set<EllenAnswer> ellenAnswers) {
		this.ellenAnswers = ellenAnswers;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}
	
	

}
