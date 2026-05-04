package edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OutboxPublisherPropertiesTest {

	private OutboxPublisherProperties properties;

	@BeforeEach
	void setUp() {
		properties = new OutboxPublisherProperties();
	}

	@Test
	void defaultValues_ShouldBeSetCorrectly() {
		// Arrange + Act + Assert
		assertEquals(5672, properties.getPort());
		assertEquals("/", properties.getVhost());
		assertEquals("fanout", properties.getExchangeType());
		assertTrue(properties.isExchangeDurable());
		assertTrue(properties.isQueueDurable());
		assertFalse(properties.isQueueExclusive());
		assertFalse(properties.isQueueAutoDelete());
		assertEquals("", properties.getBindingKey());
		assertFalse(properties.isDeclareTopology());
		assertEquals(3, properties.getPublishRetries());
		assertEquals(250L, properties.getPublishBackoffMs());
		assertEquals(5, properties.getConnectTimeoutSeconds());
		assertEquals(5, properties.getReadWriteTimeoutSeconds());
		assertEquals(50, properties.getOutboxBatchSize());
		assertEquals(1000L, properties.getOutboxPollIntervalMs());
	}

	@Test
	void setAndGetHost_ShouldStoreValue() {
		// Arrange
		String host = "localhost";

		// Act
		properties.setHost(host);

		// Assert
		assertEquals(host, properties.getHost());
	}

	@Test
	void setAndGetPort_ShouldStoreValue() {
		// Arrange
		int port = 5673;

		// Act
		properties.setPort(port);

		// Assert
		assertEquals(port, properties.getPort());
	}

	@Test
	void setAndGetUsername_ShouldStoreValue() {
		// Arrange
		String username = "guest";

		// Act
		properties.setUsername(username);

		// Assert
		assertEquals(username, properties.getUsername());
	}

	@Test
	void setAndGetPassword_ShouldStoreValue() {
		// Arrange
		String password = "secret";

		// Act
		properties.setPassword(password);

		// Assert
		assertEquals(password, properties.getPassword());
	}

	@Test
	void setAndGetExchange_ShouldStoreValue() {
		// Arrange
		String exchange = "outbox.events";

		// Act
		properties.setExchange(exchange);

		// Assert
		assertEquals(exchange, properties.getExchange());
	}

	@Test
	void setAndGetOutboxBatchSize_ShouldStoreValue() {
		// Arrange
		int batchSize = 100;

		// Act
		properties.setOutboxBatchSize(batchSize);

		// Assert
		assertEquals(batchSize, properties.getOutboxBatchSize());
	}

	@Test
	void setDeclareTopology_ShouldStoreValue() {
		// Arrange + Act
		properties.setDeclareTopology(true);

		// Assert
		assertTrue(properties.isDeclareTopology());
	}
}
