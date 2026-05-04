package edu.nur.nurtricenter_appointment.infraestructure.persistence.inbound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InboundSubscriptionPropertiesTest {

	private InboundSubscriptionProperties properties;

	@BeforeEach
	void setUp() {
		properties = new InboundSubscriptionProperties();
	}

	@Test
	void defaultValues_ShouldBeSetCorrectly() {
		// Arrange + Act + Assert
		assertTrue(properties.isEnabled());
		assertEquals("outbox.events", properties.getExchange());
		assertEquals("topic", properties.getExchangeType());
		assertTrue(properties.isExchangeDurable());
		assertEquals("citas-evaluaciones.inbound", properties.getQueue());
		assertTrue(properties.isQueueDurable());
		assertFalse(properties.isQueueExclusive());
		assertFalse(properties.isQueueAutoDelete());
	}

	@Test
	void setAndGetEnabled_ShouldStoreValue() {
		// Arrange + Act
		properties.setEnabled(false);

		// Assert
		assertFalse(properties.isEnabled());
	}

	@Test
	void setAndGetExchange_ShouldStoreValue() {
		// Arrange
		String exchange = "custom.exchange";

		// Act
		properties.setExchange(exchange);

		// Assert
		assertEquals(exchange, properties.getExchange());
	}

	@Test
	void setAndGetQueue_ShouldStoreValue() {
		// Arrange
		String queue = "custom.queue";

		// Act
		properties.setQueue(queue);

		// Assert
		assertEquals(queue, properties.getQueue());
	}

	@Test
	void setAndGetRoutingKeys_ShouldStoreList() {
		// Arrange
		List<String> keys = List.of("key1", "key2");

		// Act
		properties.setRoutingKeys(keys);

		// Assert
		assertEquals(keys, properties.getRoutingKeys());
	}

	@Test
	void setQueueExclusive_ShouldStoreValue() {
		// Arrange + Act
		properties.setQueueExclusive(true);

		// Assert
		assertTrue(properties.isQueueExclusive());
	}

	@Test
	void setQueueAutoDelete_ShouldStoreValue() {
		// Arrange + Act
		properties.setQueueAutoDelete(true);

		// Assert
		assertTrue(properties.isQueueAutoDelete());
	}
}
