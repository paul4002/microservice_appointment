package edu.nur.nurtricenter_appointment.application.appointments.attend_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class MeasurementDtoTest {

	@Test
	void constructor_WithAllFields_ShouldStoreValues() {
		// Arrange
		Double weight = 70.0;
		Double height = 170.0;
		Double imc = 24.2;
		Double bodyFat = 18.5;
		Double muscleMass = 40.0;

		// Act
		MeasurementDto dto = new MeasurementDto(weight, height, imc, bodyFat, muscleMass);

		// Assert
		assertEquals(weight, dto.weight);
		assertEquals(height, dto.height);
		assertEquals(imc, dto.imc);
		assertEquals(bodyFat, dto.bodyFat);
		assertEquals(muscleMass, dto.muscleMass);
	}

	@Test
	void defaultConstructor_ShouldCreateDtoWithNullFields() {
		// Arrange + Act
		MeasurementDto dto = new MeasurementDto();

		// Assert
		assertNotNull(dto);
		assertNull(dto.weight);
		assertNull(dto.height);
	}
}
