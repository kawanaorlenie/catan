package com.example.trade;

import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.test.utils.WebSocketTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableWebSecurity(debug = true)
public class TradeIntegrationTest {

	@LocalServerPort
	private int port;

	@Test
	public void trade() throws Exception {

		String url = "ws://localhost:{port}/gs-guide-websocket";
		String topic = "/topic/trade";
		String sendPath = "/app/trade/offer";
		Object load = new Trade();

		Consumer<Trade> assertions = t -> {
			assertTrue(t.getId() > 0);
		};

		new WebSocketTestClient<Trade, Trade>(url, port, topic, sendPath, load, Trade.class, assertions).test();

	}

}