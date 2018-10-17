package com.example.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

	@Autowired
	private Game game;

	@MessageMapping("/game")
	@SendTo("/topic/game")
	public Game game(Game game) {
		if (game != null) {
			this.game = game;
		}
		return this.game;
	}

	@MessageMapping("/initgame")
	@SendTo("/topic/game")
	public Game initGame() {
		return this.game;
	}

}