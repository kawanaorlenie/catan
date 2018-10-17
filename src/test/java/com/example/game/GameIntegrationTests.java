package com.example.game;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.game.Board;
import com.example.game.Game;
import com.example.test.utils.WebSocketTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//TODO is there a better way to test initGame?
public class GameIntegrationTests {

	@LocalServerPort
	private int port;

	@Test
	public void a_initGame() throws InterruptedException, AssertionError {
		String url = "ws://localhost:{port}/gs-guide-websocket";
		String topic = "/topic/game";
		String sendPath = "/app/initgame";
		Object load = null;

		Consumer<Game> assertions = game -> {
			assertEquals(17, game.getId());
		};

		new WebSocketTestClient<Game, Game>(url, port, topic, sendPath, load, Game.class, assertions).test();
	}

	@Test
	public void getGame() throws InterruptedException, AssertionError {
		String url = "ws://localhost:{port}/gs-guide-websocket";
		String topic = "/topic/game";
		String sendPath = "/app/game";
		Board board = Board.BoardBuilder.classicBoard();
		board.getEdges().forEach(e -> e.setPathColor("blue"));
		Object load = new Game(15, board);

		Consumer<Game> assertions = game -> {
			assertEquals(15, game.getId());
			assertEquals(19, game.getBoard().getFields().size());
			assertEquals(72, game.getBoard().getEdges().size());
			assertEquals(54, game.getBoard().getVertices().size());
			game.getBoard().getEdges().forEach(e -> assertEquals("blue", e.getPathColor()));
		};

		new WebSocketTestClient<Game, Game>(url, port, topic, sendPath, load, Game.class, assertions).test();
	}
}