package com.psych.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psych.game.model.Admin;
import com.psych.game.model.ContentWriter;
import com.psych.game.model.Game;
import com.psych.game.model.GameMode;
import com.psych.game.model.Player;
import com.psych.game.model.Question;
import com.psych.game.model.Round;
import com.psych.game.model.User;
import com.psych.game.repository.AdminRepository;
import com.psych.game.repository.ContentWritersRepository;
import com.psych.game.repository.GameModeRepository;
import com.psych.game.repository.GameRepository;
import com.psych.game.repository.PlayerRepository;
import com.psych.game.repository.QuestionRepository;
import com.psych.game.repository.RoundRepository;
import com.psych.game.repository.UserRepository;

@RestController
@RequestMapping("/dev-test")
public class HelloController {

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	GameRepository gameRepository;

	@Autowired
	RoundRepository roundRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	GameModeRepository gameModeRepository;

	@Autowired
	ContentWritersRepository contentWritersRepository;

	@GetMapping("/")
	public String hello() {
		return "Hello,GTM";
	}

	@GetMapping("/players")
	public List<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	@GetMapping("/questions/{id}")
	public Question getQuestionsById(@PathVariable(name = "id") Long id) {
		return questionRepository.findById(id).get();
	}

	@GetMapping("/players/{id}")
	public Player getPlayersById(@PathVariable(name = "id") Long id) {
		return playerRepository.findById(id).get();
	}

	@GetMapping("/questions")
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public User getUsersById(@PathVariable(name = "id") Long id) {
		return userRepository.findById(id).get();
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/games/{id}")
	public Game getGamesById(@PathVariable(name = "id") Long id) {
		return gameRepository.findById(id).get();
	}

	@GetMapping("/games")
	public List<Game> getAllGames() {
		return gameRepository.findAll();
	}

	@GetMapping("/rounds/{id}")
	public Round getRoundsById(@PathVariable(name = "id") Long id) {
		return roundRepository.findById(id).get();
	}

	@GetMapping("/rounds")
	public List<Round> getAllRounds() {
		return roundRepository.findAll();
	}

	@GetMapping("/admins/{id}")
	public Admin getAdminsById(@PathVariable(name = "id") Long id) {
		return adminRepository.findById(id).get();
	}

	@GetMapping("/admins")
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

	@GetMapping("/content-writers/{id}")
	public ContentWriter getContentWritersById(@PathVariable(name = "id") Long id) {
		return contentWritersRepository.findById(id).get();
	}

	@GetMapping("/content-writers")
	public List<ContentWriter> getAllContentWriters() {
		return contentWritersRepository.findAll();
	}

	@GetMapping("/populate")
	public String populateDB() {
		for (Player player : playerRepository.findAll()) {
			player.getGames().clear();
			player.setCurrentGame(null);
			playerRepository.save(player);
		}
		gameRepository.deleteAll();
		playerRepository.deleteAll();
		questionRepository.deleteAll();
		gameModeRepository.deleteAll();

		Player luffy = new Player.Builder().alias("Monkey D. Luffy").email("luffy@psych.com")
				.saltedHashedPassword("strawhat").build();
		playerRepository.save(luffy);
		Player robin = new Player.Builder().alias("Nico Robin").email("robin@psych.com")
				.saltedHashedPassword("poneglyph").build();
		playerRepository.save(robin);

		GameMode isThisAFact = new GameMode("Is This A Fact?",
				"https://i.pinimg.com/originals/29/cb/75/29cb75e488831ba018fe5f0925b8e39f.png",
				"is this a fact description");
		gameModeRepository.save(isThisAFact);
		gameModeRepository.save(new GameMode("Word Up",
				"https://i.pinimg.com/originals/29/cb/75/29cb75e488831ba018fe5f0925b8e39f.png", "word up description"));
		gameModeRepository.save(new GameMode("Un-Scramble",
				"https://i.pinimg.com/originals/29/cb/75/29cb75e488831ba018fe5f0925b8e39f.png",
				"unscramble descirption"));
		gameModeRepository.save(new GameMode("Movie Buff",
				"https://i.pinimg.com/originals/29/cb/75/29cb75e488831ba018fe5f0925b8e39f.png",
				"movie buff description"));

		Game game = new Game();
		game.setGameMode(isThisAFact);
		game.setLeader(luffy);
		game.getPlayers().add(luffy);
		gameRepository.save(game);

		questionRepository.save(new Question("What is the most important Poneglyph", "Rio Poneglyph", isThisAFact));
		questionRepository.save(new Question("How far can Luffy stretch?", "56 Gomu Gomus", isThisAFact));
		return "populated";
	}

}
