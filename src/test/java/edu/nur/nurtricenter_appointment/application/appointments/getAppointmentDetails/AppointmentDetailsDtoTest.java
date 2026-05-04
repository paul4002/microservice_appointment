package edu.nur.nurtricenter_appointment.application.appointments.getAppointmentDetails;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.application.appointments.getAppointmentDetails.AppointmentDetailsDto.DiagnosisDetailsDto;
import edu.nur.nurtricenter_appointment.application.appointments.getAppointmentDetails.AppointmentDetailsDto.MeasurementDetailsDto;
import edu.nur.nurtricenter_appointment.application.appointments.getAppointmentDetails.AppointmentDetailsDto.MeasurementDetailsDto.UnitMeasureDto;

public class AppointmentDetailsDtoTest {

	@Test
	void testAppointmentDetailsDtoEmptyConstructor() {
		// Arrange
		// No setup needed

		// Act
		AppointmentDetailsDto dto = new AppointmentDetailsDto();

		// Assert
		assertNull(dto.id);
		assertNull(dto.patientId);
		assertNull(dto.nutritionistId);
		assertNull(dto.type);
		assertNull(dto.creationDate);
		assertNull(dto.scheduleDate);
		assertNull(dto.cancelDate);
		assertNull(dto.status);
		assertNull(dto.attendance);
		assertNull(dto.measurement);
		assertNull(dto.diagnosis);
	}

	@Test
	void testAppointmentDetailsDtoFullConstructor() {
		// Arrange
		String id = "apt-123";
		String patientId = "pat-456";
		String nutritionistId = "nut-789";
		String type = "INITIAL_CONSULTATION";
		LocalDateTime creationDate = LocalDateTime.of(2026, 5, 1, 10, 0);
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 5, 5, 14, 30);
		LocalDateTime cancelDate = null;
		String status = "SCHEDULED";
		String attendance = "CONFIRMED";
		MeasurementDetailsDto measurement = new MeasurementDetailsDto();
		DiagnosisDetailsDto diagnosis = new DiagnosisDetailsDto();

		// Act
		AppointmentDetailsDto dto = new AppointmentDetailsDto(id, patientId, nutritionistId, type,
				creationDate, scheduleDate, cancelDate, status, attendance, measurement, diagnosis);

		// Assert
		assertEquals(id, dto.id);
		assertEquals(patientId, dto.patientId);
		assertEquals(nutritionistId, dto.nutritionistId);
		assertEquals(type, dto.type);
		assertEquals(creationDate, dto.creationDate);
		assertEquals(scheduleDate, dto.scheduleDate);
		assertNull(dto.cancelDate);
		assertEquals(status, dto.status);
		assertEquals(attendance, dto.attendance);
		assertNotNull(dto.measurement);
		assertNotNull(dto.diagnosis);
	}

	@Test
	void testAppointmentDetailsDtoPropertyAssignment() {
		// Arrange
		AppointmentDetailsDto dto = new AppointmentDetailsDto();
		String newId = "apt-999";
		String newStatus = "COMPLETED";

		// Act
		dto.id = newId;
		dto.status = newStatus;

		// Assert
		assertEquals(newId, dto.id);
		assertEquals(newStatus, dto.status);
	}

	@Test
	void testMeasurementDetailsDtoEmptyConstructor() {
		// Arrange
		// No setup needed

		// Act
		MeasurementDetailsDto measurement = new MeasurementDetailsDto();

		// Assert
		assertNull(measurement.weight);
		assertNull(measurement.height);
		assertNull(measurement.imc);
		assertNull(measurement.bodyFat);
		assertNull(measurement.muscleMass);
	}

	@Test
	void testMeasurementDetailsDtoFullConstructor() {
		// Arrange
		UnitMeasureDto weight = new UnitMeasureDto("75.5", "kg");
		UnitMeasureDto height = new UnitMeasureDto("180", "cm");
		String imc = "23.3";
		String bodyFat = "18.5";
		String muscleMass = "35.2";

		// Act
		MeasurementDetailsDto measurement = new MeasurementDetailsDto(weight, height, imc, bodyFat, muscleMass);

		// Assert
		assertNotNull(measurement.weight);
		assertEquals("75.5", measurement.weight.value);
		assertEquals("kg", measurement.weight.unit);
		assertNotNull(measurement.height);
		assertEquals("180", measurement.height.value);
		assertEquals("cm", measurement.height.unit);
		assertEquals(imc, measurement.imc);
		assertEquals(bodyFat, measurement.bodyFat);
		assertEquals(muscleMass, measurement.muscleMass);
	}

	@Test
	void testUnitMeasureDtoEmptyConstructor() {
		// Arrange
		// No setup needed

		// Act
		UnitMeasureDto unitMeasure = new UnitMeasureDto();

		// Assert
		assertNull(unitMeasure.value);
		assertNull(unitMeasure.unit);
	}

	@Test
	void testUnitMeasureDtoFullConstructor() {
		// Arrange
		String value = "70.0";
		String unit = "kg";

		// Act
		UnitMeasureDto unitMeasure = new UnitMeasureDto(value, unit);

		// Assert
		assertEquals(value, unitMeasure.value);
		assertEquals(unit, unitMeasure.unit);
	}

	@Test
	void testDiagnosisDetailsDtoEmptyConstructor() {
		// Arrange
		// No setup needed

		// Act
		DiagnosisDetailsDto diagnosis = new DiagnosisDetailsDto();

		// Assert
		assertNull(diagnosis.description);
		assertNull(diagnosis.nutritionalState);
		assertNull(diagnosis.associatedRisks);
		assertNull(diagnosis.recommendations);
		assertNull(diagnosis.goals);
		assertNull(diagnosis.comments);
	}

	@Test
	void testDiagnosisDetailsDtoFullConstructor() {
		// Arrange
		String description = "Initial nutritional assessment";
		String nutritionalState = "NORMAL";
		String associatedRisks = "Sedentary lifestyle";
		String recommendations = "Increase physical activity";
		String goals = "Maintain healthy weight";
		String comments = "Follow-up in 3 months";

		// Act
		DiagnosisDetailsDto diagnosis = new DiagnosisDetailsDto(description, nutritionalState,
				associatedRisks, recommendations, goals, comments);

		// Assert
		assertEquals(description, diagnosis.description);
		assertEquals(nutritionalState, diagnosis.nutritionalState);
		assertEquals(associatedRisks, diagnosis.associatedRisks);
		assertEquals(recommendations, diagnosis.recommendations);
		assertEquals(goals, diagnosis.goals);
		assertEquals(comments, diagnosis.comments);
	}

	@Test
	void testAppointmentDetailsDtoWithCompleteNestedObjects() {
		// Arrange
		String id = "apt-001";
		String patientId = "pat-001";
		String nutritionistId = "nut-001";

		UnitMeasureDto weight = new UnitMeasureDto("72.0", "kg");
		UnitMeasureDto height = new UnitMeasureDto("175", "cm");
		MeasurementDetailsDto measurement = new MeasurementDetailsDto(weight, height, "23.5", "20.0", "36.0");

		DiagnosisDetailsDto diagnosis = new DiagnosisDetailsDto(
			"Complete assessment",
			"NORMAL",
			"Low risk",
			"Balanced diet",
			"Maintain weight",
			"Good progress"
		);

		// Act
		AppointmentDetailsDto dto = new AppointmentDetailsDto(
			id, patientId, nutritionistId, "FOLLOW_UP",
			LocalDateTime.now(), LocalDateTime.now().plusDays(7), null,
			"SCHEDULED", "ATTENDED", measurement, diagnosis
		);

		// Assert
		assertEquals(id, dto.id);
		assertEquals(patientId, dto.patientId);
		assertNotNull(dto.measurement);
		assertEquals("72.0", dto.measurement.weight.value);
		assertEquals("kg", dto.measurement.weight.unit);
		assertEquals("23.5", dto.measurement.imc);
		assertNotNull(dto.diagnosis);
		assertEquals("Complete assessment", dto.diagnosis.description);
		assertEquals("NORMAL", dto.diagnosis.nutritionalState);
	}
}
