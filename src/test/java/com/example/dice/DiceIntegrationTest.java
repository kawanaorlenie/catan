package com.example.dice;

import static org.junit.Assert.assertNotNull;

import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.test.utils.WebSocketTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiceIntegrationTest {

	@LocalServerPort
	private int port;

	@Test
	public void roll() throws Exception {

		String url = "ws://localhost:{port}/gs-guide-websocket";
		String topic = "/topic/greetings2";
		String sendPath = "/app/roll";
		Object load = null;

		Consumer<Byte> assertions = b -> {
			assertNotNull(b);
		};

		new WebSocketTestClient<Byte, Byte>(url, port, topic, sendPath, load, Byte.class, assertions).test();

	}
}
