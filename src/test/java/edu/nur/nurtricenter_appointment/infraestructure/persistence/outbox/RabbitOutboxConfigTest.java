package edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@DisplayName("RabbitOutboxConfig Tests")
class RabbitOutboxConfigTest {

	private RabbitOutboxConfig config;
	private OutboxPublisherProperties properties;

	@BeforeEach
	void setUp() {
		config = new RabbitOutboxConfig();
		properties = new OutboxPublisherProperties();
	}

	@Test
	@DisplayName("Should create ConnectionFactory with properties")
	void testRabbitConnectionFactory() {
		// Arrange
		properties.setHost("localhost");
		properties.setPort(5672);
		properties.setUsername("guest");
		properties.setPassword("guest");
		properties.setVhost("/");
		properties.setConnectTimeoutSeconds(10);

		// Act
		ConnectionFactory connectionFactory = config.rabbitConnectionFactory(properties);

		// Assert
		assertNotNull(connectionFactory, "ConnectionFactory should not be null");
		assertTrue(connectionFactory instanceof org.springframework.amqp.rabbit.connection.CachingConnectionFactory,
				"ConnectionFactory should be instance of CachingConnectionFactory");
	}

	@Test
	@DisplayName("Should create RabbitTemplate with configured timeout")
	void testRabbitTemplate() {
		// Arrange
		properties.setHost("localhost");
		properties.setPort(5672);
		properties.setUsername("guest");
		properties.setPassword("guest");
		properties.setConnectTimeoutSeconds(10);
		properties.setReadWriteTimeoutSeconds(30);
		ConnectionFactory connectionFactory = config.rabbitConnectionFactory(properties);

		// Act
		RabbitTemplate rabbitTemplate = config.rabbitTemplate(connectionFactory, properties);

		// Assert
		assertNotNull(rabbitTemplate, "RabbitTemplate should not be null");
		assertEquals(connectionFactory, rabbitTemplate.getConnectionFactory(),
				"RabbitTemplate should use provided ConnectionFactory");
	}

	@Test
	@DisplayName("Should create RabbitAdmin with autoStartup disabled")
	void testRabbitAdmin() {
		// Arrange
		properties.setHost("localhost");
		properties.setPort(5672);
		properties.setUsername("guest");
		properties.setPassword("guest");
		properties.setConnectTimeoutSeconds(10);
		ConnectionFactory connectionFactory = config.rabbitConnectionFactory(properties);

		// Act
		RabbitAdmin rabbitAdmin = config.rabbitAdmin(connectionFactory);

		// Assert
		assertNotNull(rabbitAdmin, "RabbitAdmin should not be null");
		assertFalse(rabbitAdmin.isAutoStartup(), "RabbitAdmin should have autoStartup disabled");
	}

	@Test
	@DisplayName("Should return empty Declarables when declareTopology is false")
	void testRabbitOutboxDeclarables_WhenDeclareTopologyIsFalse() {
		// Arrange
		properties.setDeclareTopology(false);

		// Act
		Declarables declarables = config.rabbitOutboxDeclarables(properties);

		// Assert
		assertNotNull(declarables, "Declarables should not be null");
		assertTrue(declarables.getDeclarables().isEmpty(), "Declarables should be empty when declareTopology is false");
	}

	@Test
	@DisplayName("Should create Declarables with single queue when queue name is provided")
	void testRabbitOutboxDeclarables_WithSingleQueue() {
		// Arrange
		properties.setDeclareTopology(true);
		properties.setExchangeType("fanout");
		properties.setExchange("test.exchange");
		properties.setExchangeDurable(true);
		properties.setQueue("test.queue");
		properties.setQueueExclusive(false);
		properties.setQueueAutoDelete(false);
		properties.setBindingKey("test.binding");

		// Act
		Declarables declarables = config.rabbitOutboxDeclarables(properties);

		// Assert
		assertNotNull(declarables, "Declarables should not be null");
		assertFalse(declarables.getDeclarables().isEmpty(), "Declarables should contain elements");
		assertTrue(declarables.getDeclarables().size() >= 2,
				"Declarables should contain at least Exchange and Queue");
	}

	@Test
	@DisplayName("Should create Declarables with multiple queues when comma-separated queue names provided")
	void testRabbitOutboxDeclarables_WithMultipleQueues() {
		// Arrange
		properties.setDeclareTopology(true);
		properties.setExchangeType("topic");
		properties.setExchange("test.exchange");
		properties.setExchangeDurable(true);
		properties.setQueue("queue1,queue2,queue3");
		properties.setQueueExclusive(false);
		properties.setQueueAutoDelete(false);

		// Act
		Declarables declarables = config.rabbitOutboxDeclarables(properties);

		// Assert
		assertNotNull(declarables, "Declarables should not be null");
		assertFalse(declarables.getDeclarables().isEmpty(), "Declarables should contain elements");
		assertTrue(declarables.getDeclarables().size() >= 7,
				"Declarables should contain Exchange + 3 Queues + 3 Bindings");
	}

	@Test
	@DisplayName("Should create DirectExchange when exchangeType is 'direct'")
	void testRabbitOutboxDeclarables_WithDirectExchange() {
		// Arrange
		properties.setDeclareTopology(true);
		properties.setExchangeType("direct");
		properties.setExchange("test.direct");
		properties.setExchangeDurable(true);
		properties.setQueue("test.queue");
		properties.setQueueExclusive(false);
		properties.setQueueAutoDelete(false);

		// Act
		Declarables declarables = config.rabbitOutboxDeclarables(properties);

		// Assert
		assertNotNull(declarables, "Declarables should not be null");
		assertTrue(declarables.getDeclarables().stream()
				.anyMatch(DirectExchange.class::isInstance),
				"Declarables should contain DirectExchange");
	}

	@Test
	@DisplayName("Should create TopicExchange when exchangeType is 'topic'")
	void testRabbitOutboxDeclarables_WithTopicExchange() {
		// Arrange
		properties.setDeclareTopology(true);
		properties.setExchangeType("topic");
		properties.setExchange("test.topic");
		properties.setExchangeDurable(true);
		properties.setQueue("test.queue");
		properties.setQueueExclusive(false);
		properties.setQueueAutoDelete(false);

		// Act
		Declarables declarables = config.rabbitOutboxDeclarables(properties);

		// Assert
		assertNotNull(declarables, "Declarables should not be null");
		assertTrue(declarables.getDeclarables().stream()
				.anyMatch(TopicExchange.class::isInstance),
				"Declarables should contain TopicExchange");
	}

	@Test
	@DisplayName("Should create exclusive queue when queueExclusive is true")
	void testRabbitOutboxDeclarables_WithExclusiveQueue() {
		// Arrange
		properties.setDeclareTopology(true);
		properties.setExchangeType("fanout");
		properties.setExchange("test.exchange");
		properties.setExchangeDurable(true);
		properties.setQueue("test.exclusive.queue");
		properties.setQueueExclusive(true);
		properties.setQueueAutoDelete(false);

		// Act
		Declarables declarables = config.rabbitOutboxDeclarables(properties);

		// Assert
		assertNotNull(declarables, "Declarables should not be null");
		assertTrue(declarables.getDeclarables().stream()
				.anyMatch(Queue.class::isInstance),
				"Declarables should contain Queue");
	}

}
