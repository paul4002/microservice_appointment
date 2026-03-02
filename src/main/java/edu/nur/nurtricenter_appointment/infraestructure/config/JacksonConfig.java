package edu.nur.nurtricenter_appointment.infraestructure.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// @Configuration
// public class JacksonConfig {

//   @Bean
//   public ObjectMapper objectMapper() {
//     return new ObjectMapper();
//   }
// }
