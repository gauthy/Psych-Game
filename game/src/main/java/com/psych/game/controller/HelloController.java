package com.psych.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psych.game.model.Game;
import com.psych.game.model.GameMode;
import com.psych.game.model.Player;
import com.psych.game.model.Question;
import com.psych.game.model.Round;
import com.psych.game.model.User;
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
	
	@GetMapping("/")
	public String hello() {
		return "Hello,GTM";
	}
	
	@GetMapping("/players")
	public List<Player> getAllPlayers(){
		return playerRepository.findAll();
	}
	
	@GetMapping("/questions/{id}")
	public Question getQuestionsById(@PathVariable(name="id")Long id){
		return questionRepository.findById(id).get();
	}
	
	@GetMapping("/players/{id}")
	public Player getPlayersById(@PathVariable(name="id")Long id){
		return playerRepository.findById(id).get();
	}
	
	@GetMapping("/questions")
	public List<Question> getAllQuestions(){
		return questionRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUsersById(@PathVariable(name="id")Long id){
		return userRepository.findById(id).get();
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/games/{id}")
	public Game getGamesById(@PathVariable(name="id")Long id){
		return gameRepository.findById(id).get();
	}
	
	@GetMapping("/games")
	public List<Game> getAllGames(){
		return gameRepository.findAll();
	}
	
	@GetMapping("/rounds/{id}")
	public Round getRoundsById(@PathVariable(name="id")Long id){
		return roundRepository.findById(id).get();
	}
	
	@GetMapping("/rounds")
	public List<Round> getAllRounds(){
		return roundRepository.findAll();
	}
	
	@GetMapping("/populate")
    public String populateDB() {
        for(Player player: playerRepository.findAll()) {
            player.getGames().clear();
            playerRepository.save(player);
        }
        gameRepository.deleteAll();
        playerRepository.deleteAll();
        questionRepository.deleteAll();

        Player luffy = new Player.Builder()
                .alias("Monkey D. Luffy")
                .email("luffy@interviewbit.com")
                .saltedHashedPassword("strawhat")
                .build();
        playerRepository.save(luffy);
        Player robin = new Player.Builder()
                .alias("Nico Robin")
                .email("robin@interviewbit.com")
                .saltedHashedPassword("poneglyph")
                .build();
        playerRepository.save(robin);
        Game game = new Game();
        game.setGameMode(GameMode.IS_THIS_A_FACT);
        game.setLeader(luffy);
        game.getPlayers().add(luffy);
        gameRepository.save(game);
        questionRepository.save(new Question(
                "What is the most important Poneglyph",
                "Rio Poneglyph",
                GameMode.IS_THIS_A_FACT
        ));
        questionRepository.save(new Question(
                "How far can Luffy stretch?",
                "56 Gomu Gomus",
                GameMode.IS_THIS_A_FACT
        ));
        return "populated";
    }

	
	
	
}
