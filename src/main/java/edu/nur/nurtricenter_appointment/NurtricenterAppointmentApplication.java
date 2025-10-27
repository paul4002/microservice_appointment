package edu.nur.nurtricenter_appointment;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.CommandHandlers;
import an.awesome.pipelinr.Notification;
import an.awesome.pipelinr.NotificationHandlers;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories.NutritionistJpaRepository;

@SpringBootApplication(scanBasePackages = "edu.nur.nurtricenter_appointment")
@EntityScan({"edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel", "edu.nur.nurtricenter_appointment.infraestructure.persistence.persistenceModel"})
@EnableJpaRepositories(basePackages = { "edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories" })
public class NurtricenterAppointmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(NurtricenterAppointmentApplication.class, args);
	}

	@Bean
	public INutritionistRepository nutritionistRepository() {
		return new NutritionistJpaRepository();
	}

	@Bean
  Pipeline pipeline(ObjectProvider<Command.Handler> commandHandlers, ObjectProvider<Notification.Handler> notificationHandlers, ObjectProvider<Command.Middleware> middlewares) {
    return new Pipelinr()
      .with((CommandHandlers) commandHandlers::stream)
      .with((NotificationHandlers) notificationHandlers::stream)
      .with((Command.Middlewares) middlewares::orderedStream);
  }
}
