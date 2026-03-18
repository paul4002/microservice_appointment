package edu.nur.nurtricenter_appointment.infraestructure.config;

import java.util.List;

import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return JsonMapper.builder().addModule(new JavaTimeModule()).build();
	}


	@Bean
	public SimpleMessageConverter converter() {
			SimpleMessageConverter converter = new SimpleMessageConverter();
			converter.setAllowedListPatterns(List.of("xyz.test.common.*", "java.util.*"));
			return converter;
	}
}
