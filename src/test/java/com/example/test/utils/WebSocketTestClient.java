package com.example.test.utils;

import static org.assertj.core.api.Assertions.fail;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

public class WebSocketTestClient<T, C> {

	private int port;
	private String url;
	private String topic;
	private String sendPath;
	private Object load;
	private WebSocketStompClient stompClient;
	private Consumer<C> assertions;
	private Class<C> type;

	public WebSocketTestClient(String url, int port, String topic, String sendPath, Object load, Class<C> type,
			Consumer<C> assertions) {
		
		this.stompClient = new WebSocketStompClient(
				new SockJsClient(Arrays.asList(new WebSocketTransport(new StandardWebSocketClient()))));
		this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		this.url = url;
		this.port = port;
		this.topic = topic;
		this.sendPath = sendPath;
		this.load = load;
		this.assertions = assertions;
		this.type = type;
	}

	public void test() throws InterruptedException, AssertionError {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		TestSessionHandler<T, C> handler = new TestSessionHandler<>(failure, latch, assertions);

		
		WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
		String auth = "user" + ":" + "password";
		headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(auth.getBytes())));
		
		this.stompClient.connect(url, headers, handler, this.port);

		if (latch.await(3, TimeUnit.SECONDS)) {
			if (failure.get() != null) {
				throw new AssertionError("", failure.get());
			}
		} else {
			fail("Greeting not received");
		}

	}

	private class TestSessionHandler<U, B> extends StompSessionHandlerAdapter {

		private final AtomicReference<Throwable> failure;
		private CountDownLatch latch;
		private Consumer<? super B> predicate;

		public TestSessionHandler(AtomicReference<Throwable> failure, CountDownLatch latch,
				Consumer<? super B> predicate) {
			this.failure = failure;
			this.latch = latch;
			this.predicate = predicate;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			this.failure.set(new Exception(headers.toString()));
		}

		@Override
		public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
			this.failure.set(ex);
		}

		@Override
		public void handleTransportError(StompSession session, Throwable ex) {
			this.failure.set(ex);
		}

		@Override
		public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
			session.subscribe(topic, new MyStompFrameHandler<U, B>(failure, latch, session, predicate));
			try {
				session.send(sendPath, load);
			} catch (Throwable t) {
				failure.set(t);
				latch.countDown();
			}
		}
	}

	private class MyStompFrameHandler<W, A> implements StompFrameHandler {

		private AtomicReference<Throwable> failure;
		private CountDownLatch latch;
		private StompSession session;
		private Consumer<? super A> predicate;

		public MyStompFrameHandler(AtomicReference<Throwable> failure, CountDownLatch latch, StompSession session,
				Consumer<? super A> predicate) {
			this.failure = failure;
			this.latch = latch;
			this.session = session;
			this.predicate = predicate;
		}

		@Override
		public Type getPayloadType(StompHeaders arg0) {
			return type;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			A trade = (A) payload;
			try {
				predicate.accept(trade);
			} catch (Throwable t) {
				failure.set(t);
			} finally {
				session.disconnect();
				latch.countDown();
			}
		}

	}
}
