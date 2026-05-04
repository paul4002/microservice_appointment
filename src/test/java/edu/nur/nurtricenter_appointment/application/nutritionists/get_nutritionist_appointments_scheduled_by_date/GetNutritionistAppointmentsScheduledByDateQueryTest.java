package edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist_appointments_scheduled_by_date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class GetNutritionistAppointmentsScheduledByDateQueryTest {

	@Test
	void constructor_ShouldStoreNutritionistIdAndDate() {
		// Arrange
		UUID nutritionistId = UUID.randomUUID();
		LocalDate date = LocalDate.of(2026, 5, 10);

		// Act
		GetNutritionistAppointmentsScheduledByDateQuery query =
				new GetNutritionistAppointmentsScheduledByDateQuery(nutritionistId, date);

		// Assert
		assertEquals(nutritionistId, query.nutritionistId());
		assertEquals(date, query.date());
	}

	@Test
	void constructor_ShouldCreateNonNullQuery() {
		// Arrange
		UUID nutritionistId = UUID.randomUUID();
		LocalDate date = LocalDate.now();

		// Act
		GetNutritionistAppointmentsScheduledByDateQuery query =
				new GetNutritionistAppointmentsScheduledByDateQuery(nutritionistId, date);

		// Assert
		assertNotNull(query);
	}
}
