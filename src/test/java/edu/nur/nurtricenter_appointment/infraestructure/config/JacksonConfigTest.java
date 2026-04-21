package edu.nur.nurtricenter_appointment.infraestructure.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonConfigTest {

	private JacksonConfig config;

	@BeforeEach
	void setUp() {
		config = new JacksonConfig();
	}

	@Test
	void objectMapper_ShouldReturnNonNullMapper() {
		// Arrange + Act
		ObjectMapper objectMapper = config.objectMapper();

		// Assert
		assertNotNull(objectMapper);
	}

	@Test
	void objectMapper_ShouldSupportJavaTimeModule() {
		// Arrange + Act
		ObjectMapper objectMapper = config.objectMapper();

		// Assert
		assertNotNull(objectMapper);
		assertNotNull(objectMapper.getRegisteredModuleIds());
	}

	@Test
	void converter_ShouldReturnNonNullConverter() {
		// Arrange + Act
		SimpleMessageConverter converter = config.converter();

		// Assert
		assertNotNull(converter);
	}
}
