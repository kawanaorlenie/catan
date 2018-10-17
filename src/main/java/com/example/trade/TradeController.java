package com.example.trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TradeController {

	@Autowired
	private TradeServie tradeServie;

	@Autowired
	private SimpMessagingTemplate template;

	@MessageMapping("/trade/offer")
	@SendTo("/topic/trade")
	public Trade offer(Trade trade) {
		tradeServie.offer(trade);
		return trade;
	}

	@MessageMapping("/trade/accept")
	@SendTo("/topic/trade")
	public Trade accept(Trade trade) {
		tradeServie.transfer(trade);

		this.template.convertAndSend("/topic/greetings2", "trade done!");

		return trade;
	}
}
