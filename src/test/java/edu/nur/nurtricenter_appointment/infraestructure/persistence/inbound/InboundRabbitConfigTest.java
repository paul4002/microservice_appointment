package edu.nur.nurtricenter_appointment.infraestructure.persistence.inbound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.TopicExchange;

public class InboundRabbitConfigTest {

	private InboundRabbitConfig inboundRabbitConfig;
	private InboundSubscriptionProperties properties;

	@BeforeEach
	void setUp() {
		inboundRabbitConfig = new InboundRabbitConfig();
		properties = new InboundSubscriptionProperties();
	}

	@Test
	void inboundRabbitDeclarables_WhenDisabled_ShouldReturnEmptyDeclarables() {
		// Arrange
		properties.setEnabled(false);

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().isEmpty());
	}

	@Test
	void inboundRabbitDeclarables_WhenQueueIsNull_ShouldReturnEmptyDeclarables() {
		// Arrange
		properties.setEnabled(true);
		properties.setQueue(null);

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().isEmpty());
	}

	@Test
	void inboundRabbitDeclarables_WhenQueueIsBlank_ShouldReturnEmptyDeclarables() {
		// Arrange
		properties.setEnabled(true);
		properties.setQueue("   ");

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().isEmpty());
	}

	@Test
	void inboundRabbitDeclarables_WhenExchangeIsNull_ShouldReturnEmptyDeclarables() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange(null);

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().isEmpty());
	}

	@Test
	void inboundRabbitDeclarables_WhenExchangeIsBlank_ShouldReturnEmptyDeclarables() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("   ");

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().isEmpty());
	}

	@Test
	void inboundRabbitDeclarables_WithValidConfigurationNoRoutingKeys_ShouldCreateExchangeAndQueue() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setRoutingKeys(List.of());

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertEquals(2, result.getDeclarables().size());
	}

	@Test
	void inboundRabbitDeclarables_WithNullRoutingKeys_ShouldCreateExchangeAndQueue() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setRoutingKeys(null);

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertEquals(2, result.getDeclarables().size());
	}

	@Test
	void inboundRabbitDeclarables_WithValidRoutingKeys_ShouldCreateExchangeQueueAndBindings() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setRoutingKeys(List.of("key1", "key2", "key3"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertEquals(5, result.getDeclarables().size());
	}

	@Test
	void inboundRabbitDeclarables_WithBlankRoutingKeysInList_ShouldIgnoreBlankKeys() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setRoutingKeys(List.of("key1", "   ", "key2", "", "key3"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertEquals(5, result.getDeclarables().size());
	}

	@Test
	void inboundRabbitDeclarables_WithNullRoutingKeyInList_ShouldHandleNullKey() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		List<String> keysWithNull = new ArrayList<>();
		keysWithNull.add("key1");
		keysWithNull.add(null);
		keysWithNull.add("key2");
		properties.setRoutingKeys(keysWithNull);

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertEquals(4, result.getDeclarables().size());
	}

	@Test
	void inboundRabbitDeclarables_WithTopicExchange_ShouldCreateTopicExchange() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setExchangeType("topic");
		properties.setRoutingKeys(List.of("key1"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().stream().anyMatch(d -> d instanceof TopicExchange));
	}

	@Test
	void inboundRabbitDeclarables_WithFanoutExchange_ShouldCreateFanoutExchange() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setExchangeType("fanout");
		properties.setRoutingKeys(List.of("key1"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().stream().anyMatch(d -> d instanceof FanoutExchange));
	}

	@Test
	void inboundRabbitDeclarables_WithDirectExchange_ShouldCreateDirectExchange() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setExchangeType("direct");
		properties.setRoutingKeys(List.of("key1"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().stream().anyMatch(d -> d instanceof DirectExchange));
	}

	@Test
	void inboundRabbitDeclarables_WithHeadersExchange_ShouldCreateHeadersExchange() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setExchangeType("headers");
		properties.setRoutingKeys(List.of("key1"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().stream().anyMatch(d -> d instanceof HeadersExchange));
	}

	@Test
	void inboundRabbitDeclarables_WithDefaultExchangeType_ShouldCreateTopicExchange() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setExchangeType(null);
		properties.setRoutingKeys(List.of("key1"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().stream().anyMatch(d -> d instanceof TopicExchange));
	}

	@Test
	void inboundRabbitDeclarables_WithExchangeTypeInUpperCase_ShouldCreateCorrectExchange() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setExchangeType("FANOUT");
		properties.setRoutingKeys(List.of("key1"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().stream().anyMatch(d -> d instanceof FanoutExchange));
	}

	@Test
	void inboundRabbitDeclarables_WithExchangeTypeWithWhitespace_ShouldHandleWhitespace() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setExchangeType("  direct  ");
		properties.setRoutingKeys(List.of("key1"));

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertTrue(result.getDeclarables().stream().anyMatch(d -> d instanceof DirectExchange));
	}

	@Test
	void inboundRabbitDeclarables_WithQueueDurableSettings_ShouldPreserveQueueProperties() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setQueueDurable(true);
		properties.setQueueExclusive(false);
		properties.setQueueAutoDelete(false);

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertFalse(result.getDeclarables().isEmpty());
		assertEquals(2, result.getDeclarables().size());
	}

	@Test
	void inboundRabbitDeclarables_WithExchangeDurableSettings_ShouldPreserveExchangeProperties() {
		// Arrange
		properties.setEnabled(true);
		properties.setExchange("test.exchange");
		properties.setQueue("test.queue");
		properties.setExchangeDurable(false);

		// Act
		Declarables result = inboundRabbitConfig.inboundRabbitDeclarables(properties);

		// Assert
		assertFalse(result.getDeclarables().isEmpty());
		assertEquals(2, result.getDeclarables().size());
	}
}
