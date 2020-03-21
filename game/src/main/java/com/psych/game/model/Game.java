package com.psych.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psych.game.Utils;
import com.psych.game.exceptions.InvalidActionException;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Entity
@Table(name = "games")
public class Game extends Auditable {
	@ManyToMany
	@JsonIdentityReference
	private Set<Player> players = new HashSet<>();

	@JsonIdentityReference
	@ManyToOne
	@NotNull
	private GameMode gameMode;

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	@JsonManagedReference
	@OrderBy(value = "round_number asc")
	private List<Round> rounds = new ArrayList<>();

	private int numRounds = 10;

	private Boolean hasEllen = false;

	@NotNull
	@ManyToOne
	@JsonIdentityReference
	private Player leader;

	@ManyToMany(cascade = CascadeType.ALL)
	@JsonManagedReference
	private Map<Player, Stat> playerStats = new HashMap<>();

	@Enumerated(EnumType.STRING)
	private GameStatus gameStatus=GameStatus.PLAYERS_JOINING;

	@ManyToMany
	@JsonIdentityReference
	private Set<Player> readyPlayers = new HashSet<>();

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	public int getNumRounds() {
		return numRounds;
	}

	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}

	public Boolean getHasEllen() {
		return hasEllen;
	}

	public void setHasEllen(Boolean hasEllen) {
		this.hasEllen = hasEllen;
	}

	public Player getLeader() {
		return leader;
	}

	public void setLeader(Player leader) {
		this.leader = leader;
	}

	public Map<Player, Stat> getPlayerStats() {
		return playerStats;
	}

	public void setPlayerStats(Map<Player, Stat> playerStats) {
		this.playerStats = playerStats;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Set<Player> getReadyPlayers() {
		return readyPlayers;
	}

	public void setReadyPlayers(Set<Player> readyPlayers) {
		this.readyPlayers = readyPlayers;
	}

	public Game(@NotNull GameMode gameMode, int numRounds, Boolean hasEllen, @NotNull Player leader) {
		super();
		this.gameMode = gameMode;
		this.numRounds = numRounds;
		this.hasEllen = hasEllen;
		this.leader = leader;
		try {
			addPlayer(leader);
		} catch (InvalidActionException e) {
		}
	}

	public Game() {
		// TODO Auto-generated constructor stub
	}

	public void addPlayer(Player player) throws InvalidActionException {
		System.out.println(player.toString());
		if (!gameStatus.equals(GameStatus.PLAYERS_JOINING)) {
			throw new InvalidActionException("Game is already started");
		}
		players.add(player);
		player.setCurrentGame(this);
	}

	public void removePlayer(Player player) throws InvalidActionException {
		if (!players.contains(player)) {
			throw new InvalidActionException("Player is not part of the game");
		}
		players.remove(player);
		if (player.getCurrentGame().equals(this)) {
			player.setCurrentGame(null);
		}

		if (players.size() == 0 || (players.size() == 1 && !gameStatus.equals(GameStatus.PLAYERS_JOINING))) {
			endGame();
		}

	}

	private void startGame() throws InvalidActionException {
		// TODO Auto-generated method stub
		if (!players.equals(leader)) {
			throw new InvalidActionException("Player is not part of the game");
		}
		startNewRound();

	}

	private void startNewRound() {
		gameStatus = GameStatus.SUBMITTING_ANSWERS;
		Question question = Utils.getRandomQuestion(gameMode);
		Round round = new Round(this, question, rounds.size() + 1);
		if (hasEllen) {
			round.setEllenAnswer(Utils.getEllenAnswer(question));
		}
		rounds.add(round);
	}

	private void endGame() {
		gameStatus = GameStatus.ENDED;
		for (Player player : players) {
			if (player.getCurrentGame().equals(this)) {
				player.setCurrentGame(null);
			}
		}
	}

	public void submitAnswer(Player player, String answer) throws InvalidActionException {
		if (!players.contains(player)) {
			throw new InvalidActionException("Player is not part of the game");
		}

		if (answer.length() == 0) {
			throw new InvalidActionException("Answer cannot be empty");
		}

		if (!gameStatus.equals(GameStatus.SUBMITTING_ANSWERS)) {
			throw new InvalidActionException("Game is not accepting the answers");
		}

		Round currentRound = getCurrentRound();

		currentRound.submitAnswer(player, answer);

		if (currentRound.allSubmittedAnswers(players.size())) {
			if (rounds.size() < numRounds) {
				gameStatus = GameStatus.SELECTING_ANSWERS;
			} else {
				endGame();
			}
		}
	}

	public void selectingAnswer(Player player, PlayerAnswer answer) throws InvalidActionException {
		if (!players.contains(player)) {
			throw new InvalidActionException("Player is not part of the game");
		}

		if (!gameStatus.equals(GameStatus.SELECTING_ANSWERS)) {
			throw new InvalidActionException("Game is not accepting the answers");
		}

		Round currentRound = getCurrentRound();

		currentRound.selectAnswer(player, answer);

		if (currentRound.allSelectedAnswers(players.size())) {
			gameStatus = GameStatus.WAITING_FOR_READY;
		}
	}

	public void playerIsReady(Player player) throws InvalidActionException {
		if (!players.contains(player)) {
			throw new InvalidActionException("Player is not part of the game");
		}

		if (!gameStatus.equals(GameStatus.WAITING_FOR_READY)) {
			throw new InvalidActionException("Game is not waiting for the answers");
		}
		readyPlayers.add(player);

		if (readyPlayers.size() == players.size())
			startNewRound();
	}

	public void playerIsNotReady(Player player) throws InvalidActionException {
		if (!players.contains(player)) {
			throw new InvalidActionException("Player is not part of the game");
		}

		if (!gameStatus.equals(GameStatus.WAITING_FOR_READY)) {
			throw new InvalidActionException("Game is not waiting for the answers");
		}
		readyPlayers.remove(player);
	}

	private Round getCurrentRound() throws InvalidActionException {
		if (rounds.size() == 0)
			throw new InvalidActionException("Game has not yet started");

		return rounds.get(rounds.size() - 1);

	}

	public JSONObject getGameState() {
		JSONObject state = new JSONObject();
		state.put("id", getId());
		state.put("numRounds", numRounds);
		state.put("mode", gameMode.getName());
		JSONArray playerData = new JSONArray();
		for (Player player : players) {
			JSONObject data = new JSONObject();
			data.put("alias", player.getAlias());
			playerData.add(data);
		}
		state.put("players", playerData);
		return state;
	}

}
