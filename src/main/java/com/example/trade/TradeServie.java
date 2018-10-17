package com.example.trade;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.example.trade.Trade.TradeStatus;

@Service
public class TradeServie {

	private AtomicInteger id = new AtomicInteger();

	public boolean transfer(Trade trade) {
		trade.setStatus(TradeStatus.DONE);
		return false;
	}

	public void offer(Trade trade) {
		trade.setId(id.incrementAndGet());
		trade.setStatus(TradeStatus.ACTIVE);
	}
}
