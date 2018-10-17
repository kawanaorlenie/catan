package com.example.trade;

import java.util.Set;

public class Trade {

	enum TradeStatus {
		ACTIVE, CANCELED, DONE
	}

	private int id;
	private Set<Card> give;
	private Set<Card> receive;
	private TradeStatus status;
	private int fromId;
	private int toId;
	
	

	public Trade() {
		super();
	}

	public Trade(int id) {
		super();
		this.id = id;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public TradeStatus getStatus() {
		return status;
	}

	public void setStatus(TradeStatus status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Card> getGive() {
		return give;
	}

	public void setGive(Set<Card> give) {
		this.give = give;
	}

	public Set<Card> getReceive() {
		return receive;
	}

	public void setReceive(Set<Card> receive) {
		this.receive = receive;
	}

}
