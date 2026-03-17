package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.MeasurementDto;
import edu.nur.nurtricenter_appointment.application.utils.MeasurementMapper;
import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;

public class MeasurementMapperTest {
	@Test
	void shouldReturnEmptyMeasurementWhenDtoIsNull() {
		Measurement result = MeasurementMapper.from(null);

		assertNotNull(result, "Measurement no debe ser null");
		assertNull(result.getWeight(), "Weight debe ser null");
		assertNull(result.getHeight(), "Height debe ser null");
		assertNull(result.getImc(), "IMC debe ser null");
		assertNull(result.getBodyFat(), "BodyFat debe ser null");
		assertNull(result.getMuscleMass(), "MuscleMass debe ser null");
	}

	@Test
	void shouldMapMeasurementDtoToMeasurement() {
		MeasurementDto dto = new MeasurementDto();
		dto.weight = 70.0;
		dto.height = 175.0;
		dto.imc = 22.9;
		dto.bodyFat = 15.0;
		dto.muscleMass = 40.0;

		Measurement result = MeasurementMapper.from(dto);

		assertNotNull(result);
		assertEquals(70.0, result.getWeight().value().value());
		assertEquals(175.0, result.getHeight().value().value());
		assertEquals(22.9, result.getImc().value());
		assertEquals(15.0, result.getBodyFat().value().value());
		assertEquals(40.0, result.getMuscleMass().value().value());
	}
}
