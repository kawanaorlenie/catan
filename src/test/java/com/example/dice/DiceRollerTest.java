package com.example.dice;

import static org.junit.Assert.*;

import org.junit.Test;

public class DiceRollerTest {

	@Test
	public void verifyNumberBetweenOneAndTwelve() {
		DiceRoller diceRoller = new DiceRoller();
		byte roll = diceRoller.roll();
		System.out.println(roll);
		assertTrue(roll >= 1);
		assertTrue(roll <= 12);
	}

}
