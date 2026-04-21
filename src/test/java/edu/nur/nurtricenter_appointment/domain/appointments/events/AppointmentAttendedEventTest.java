package edu.nur.nurtricenter_appointment.domain.appointments.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;
import edu.nur.nurtricenter_appointment.domain.appointments.Percentage;
import edu.nur.nurtricenter_appointment.domain.appointments.events.AppointmentAttendedEvent.DiagnosisPayload;
import edu.nur.nurtricenter_appointment.domain.appointments.events.AppointmentAttendedEvent.MeasurementPayload;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;
import edu.nur.nurtricenter_appointment.domain.diagnosis.DiagnosisNutritionalState;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;

public class AppointmentAttendedEventTest {

	private UUID appointmentId;
	private Measurement measurement;
	private Diagnosis diagnosis;
	private AppointmentAttendedEvent event;

	@BeforeEach
	void setUp() {
		appointmentId = UUID.randomUUID();
		measurement = new Measurement(
				new DecimalValue(70.0),
				new DecimalValue(170.0),
				new DecimalValue(24.2),
				new Percentage(new DecimalValue(18.5)),
				new Percentage(new DecimalValue(40.0))
		);
		diagnosis = new Diagnosis(
				"Paciente con peso normal",
				DiagnosisNutritionalState.NORMAL_WEIGHT,
				"Sin riesgos significativos",
				"Mantener hábitos saludables",
				"Mantener peso actual",
				"Revisión en 3 meses"
		);
		event = new AppointmentAttendedEvent(appointmentId, measurement, diagnosis);
	}

	@Test
	void getAppointmentId_ShouldReturnAppointmentId() {
		// Arrange
		UUID expectedId = appointmentId;

		// Act
		UUID result = event.getAppointmentId();

		// Assert
		assertEquals(expectedId, result);
	}

	@Test
	void getMeasurement_ShouldReturnMeasurement() {
		// Arrange
		Measurement expectedMeasurement = measurement;

		// Act
		Measurement result = event.getMeasurement();

		// Assert
		assertEquals(expectedMeasurement, result);
	}

	@Test
	void getDiagnosis_ShouldReturnDiagnosis() {
		// Arrange
		Diagnosis expectedDiagnosis = diagnosis;

		// Act
		Diagnosis result = event.getDiagnosis();

		// Assert
		assertEquals(expectedDiagnosis, result);
	}

	@Test
	void getAggregateId_ShouldReturnAppointmentIdAsString() {
		// Arrange
		String expectedAggregateId = appointmentId.toString();

		// Act
		String result = event.getAggregateId();

		// Assert
		assertEquals(expectedAggregateId, result);
	}

	@Test
	void getAggregateType_ShouldReturnAppointment() {
		// Arrange
		String expectedType = "Appointment";

		// Act
		String result = event.getAggregateType();

		// Assert
		assertEquals(expectedType, result);
	}

	@Test
	void getEventName_ShouldReturnEventName() {
		// Arrange
		String expectedEventName = "citas-evaluaciones.cita.atendida";

		// Act
		String result = event.getEventName();

		// Assert
		assertEquals(expectedEventName, result);
	}

	@Test
	void getPayload_ShouldReturnNonNullPayload() {
		// Arrange + Act
		Object result = event.getPayload();

		// Assert
		assertNotNull(result);
	}

	@Test
	void measurementPayload_ShouldMapMeasurementFieldsFromMeasurement() {
		// Arrange
		Measurement source = measurement;

		// Act
		MeasurementPayload payload = new MeasurementPayload(source);

		// Assert
		assertEquals(source.getWeight().value().value(), payload.peso());
		assertEquals(source.getHeight().value().value(), payload.altura());
		assertEquals(source.getImc().value(), payload.imc());
		assertEquals(source.getBodyFat().value().value(), payload.porcentajeGrasaCorporal());
		assertEquals(source.getMuscleMass().value().value(), payload.porcentajeMasaMuscular());
	}

	@Test
	void diagnosisPayload_ShouldMapDiagnosisFieldsFromDiagnosis() {
		// Arrange
		Diagnosis source = diagnosis;

		// Act
		DiagnosisPayload payload = new DiagnosisPayload(source);

		// Assert
		assertEquals(source.getDescription(), payload.descripcion());
		assertEquals(source.getNutritionalState().getLabel(), payload.estadoNutricional());
		assertEquals(source.getAssociatedRisks(), payload.riesgosAsociados());
		assertEquals(source.getRecommendations(), payload.recomendaciones());
		assertEquals(source.getGoals(), payload.objetivo());
		assertEquals(source.getComments(), payload.comentarios());
	}
}
