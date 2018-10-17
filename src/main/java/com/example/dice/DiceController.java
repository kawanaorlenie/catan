package com.example.dice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DiceController {

	@Autowired
	private DiceRoller diceRoller;

	@MessageMapping("/roll")
	@SendTo("/topic/greetings2")
	public byte rollDice() {
		return diceRoller.roll();
	}
}
