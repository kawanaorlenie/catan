package com.example.dice;

import org.springframework.stereotype.Service;

@Service
public class DiceRoller {

	public byte roll() {
		return (byte) (Math.random() * 12);
	}

}
