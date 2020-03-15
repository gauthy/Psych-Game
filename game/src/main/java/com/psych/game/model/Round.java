package com.psych.game.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psych.game.exceptions.InvalidActionException;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rounds")
public class Round extends Auditable {

	@ManyToOne
	@Getter
	@Setter
	@JsonBackReference
	@NotNull
	private Game game;

	@ManyToOne
	@NotNull
	@Getter
	@Setter
	@JsonIdentityReference
	private Question question;

	@ManyToMany(cascade = CascadeType.ALL)
	@Getter
	@Setter
	@JsonManagedReference
	private Map<Player, PlayerAnswer> submittedAnswers = new HashMap<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@Getter
	@Setter
	@JsonManagedReference
	private Map<Player, PlayerAnswer> selectedAnswers = new HashMap<>();

	@ManyToOne
	@Getter
	@Setter
	@JsonIdentityReference
	private EllenAnswer ellenAnswer;

	@NotNull
	@Getter
	@Setter
	private int roundNumber;
	
	

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Map<Player, PlayerAnswer> getSubmittedAnswers() {
		return submittedAnswers;
	}

	public void setSubmittedAnswers(Map<Player, PlayerAnswer> submittedAnswers) {
		this.submittedAnswers = submittedAnswers;
	}

	public Map<Player, PlayerAnswer> getSelectedAnswers() {
		return selectedAnswers;
	}

	public void setSelectedAnswers(Map<Player, PlayerAnswer> selectedAnswers) {
		this.selectedAnswers = selectedAnswers;
	}

	public EllenAnswer getEllenAnswer() {
		return ellenAnswer;
	}

	public void setEllenAnswer(EllenAnswer ellenAnswer) {
		this.ellenAnswer = ellenAnswer;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public Round() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Round(@NotNull Game game, @NotNull Question question, @NotNull int roundNumber) {
		super();
		this.game = game;
		this.question = question;
		this.roundNumber = roundNumber;
	}

	public Round(@NotNull Game game, @NotNull Question question, EllenAnswer ellenAnswer, @NotNull int roundNumber) {
		super();
		this.game = game;
		this.question = question;
		this.ellenAnswer = ellenAnswer;
		this.roundNumber = roundNumber;
	}

	public void submitAnswer(Player player, String answer) throws InvalidActionException {
		if (submittedAnswers.containsKey(player)) {
			throw new InvalidActionException("Player has already submitted the answers");
		}
		for (PlayerAnswer existingAnswer : submittedAnswers.values()) {
			if (answer.equals(existingAnswer)) {
				throw new InvalidActionException("Duplicate answer is submitted");
			}
		}
		submittedAnswers.put(player, new PlayerAnswer(this, player, answer));
	}

	public boolean allSubmittedAnswers(int numPlayers) {
		return submittedAnswers.size() == numPlayers;
	}

	public void selectAnswer(Player player, PlayerAnswer selectedAnswer) throws InvalidActionException {
		if (selectedAnswers.containsKey(player)) {
			throw new InvalidActionException("Player has already selected the answers");
		}

		if (selectedAnswer.getPlayer().equals(player)) {
			throw new InvalidActionException("Player cannnot select his own answers");
		}
		
		selectedAnswers.put(player, selectedAnswer);

	}

	public boolean allSelectedAnswers(int numPlayers) {
		return (selectedAnswers.size()==numPlayers);
	}

}
