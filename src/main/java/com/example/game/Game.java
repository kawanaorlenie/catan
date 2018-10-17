package com.example.game;

import org.springframework.stereotype.Component;

import com.example.game.Board.BoardBuilder;

@Component
public class Game {

	private int id;
	private Board board;

	public int getId() {
		return this.id;
	}

	public Board getBoard() {
		return this.board;
	}

	public Game() {
		board = Board.BoardBuilder.classicBoard();
		id = 17;
	}

	public Game(int id) {
		this.id = id;
	}

	public Game(int id, Board board) {
		this.id = id;
		this.board = board;
	}
}
