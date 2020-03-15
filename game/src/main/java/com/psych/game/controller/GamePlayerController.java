package com.psych.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psych.game.exceptions.InvalidActionException;
import com.psych.game.model.Player;
import com.psych.game.repository.PlayerRepository;

@RestController
@RequestMapping("/play")
public class GamePlayerController {

	@Autowired
	PlayerRepository playerRepository;
	
	@GetMapping("/")
	public String play(Authentication authentication) {
		return authentication.getName();
	}
	
	
	@GetMapping("/submit-answer/{answer}")
	public void submitAnswer(Authentication authentication,@PathVariable(name="answer")String answer) throws InvalidActionException {
		Player player = playerRepository.findByEmail(authentication.getName()).get();
		player.getCurrentGame().submitAnswer(player, answer);
	}
	
}
