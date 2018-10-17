package com.example.greeting;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.greeting.Greeting;
import com.example.greeting.HelloMessage;
import com.example.test.utils.WebSocketTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingIntegrationTests {

	@LocalServerPort
	private int port;

	@Test
	public void getGreeting2() throws InterruptedException, AssertionError {
		String url = "ws://localhost:{port}/gs-guide-websocket";
		String topic = "/topic/greetings";
		String sendPath = "/app/hello";
		Object load = new HelloMessage("Spring");

		Consumer<Greeting> assertions = g -> {
			assertEquals("Hello, Spring!", g.getContent());
		};

		new WebSocketTestClient<HelloMessage, Greeting>(url, port, topic, sendPath, load, Greeting.class, assertions)
				.test();
	}

}
