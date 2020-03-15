package com.psych.game.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "playeranswers")
public class PlayerAnswer extends Auditable {

	@NotNull
	@ManyToOne
	@JsonBackReference
	private Round round;

	@NotNull
	@ManyToOne
	@JsonIdentityReference
	private Player player;

	@NotBlank
	private String answer;

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public PlayerAnswer(@NotNull Round round, @NotNull Player player, @NotBlank String answer) {
		super();
		this.round = round;
		this.player = player;
		this.answer = answer;
	}
	
	

}
