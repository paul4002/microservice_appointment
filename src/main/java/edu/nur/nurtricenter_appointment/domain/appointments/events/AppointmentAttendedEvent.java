package edu.nur.nurtricenter_appointment.domain.appointments.events;

import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.DomainEvent;
import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;

public class AppointmentAttendedEvent extends DomainEvent {
	private final UUID appointmentId;
	private final Measurement measurement;
	private final Diagnosis diagnosis;

	public AppointmentAttendedEvent(UUID appointmentId, Measurement measurement, Diagnosis diagnosis) {
		this.appointmentId = appointmentId;
		this.measurement = measurement;
		this.diagnosis = diagnosis;
	}

	public UUID getAppointmentId() {
		return appointmentId;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public Diagnosis getDiagnosis() {
		return diagnosis;
	}

	@Override
	public String getAggregateId() {
		return appointmentId.toString();
	}

	@Override
	public String getAggregateType() {
		return "Appointment";
	}

	@Override
	public String getEventName() {
		return "citas-evaluaciones.cita.atendida";
	}

	@Override
	public Object getPayload() {
		return new Payload(appointmentId.toString(), new MeasurementPayload(measurement), new DiagnosisPayload(diagnosis));
	}

	private record Payload(String citaId, Object mediciones, Object diagnostico) {}

	public record MeasurementPayload(
		Double peso,
		Double altura,
		Double imc,
		Double porcentajeGrasaCorporal,
		Double porcentajeMasaMuscular
	) {
			public MeasurementPayload(Measurement m) {
					this(
							m.getWeight().value().value(),
							m.getHeight().value().value(),
							m.getImc().value(),
							m.getBodyFat().value().value(),
							m.getMuscleMass().value().value()
					);
			}
	}

	public record DiagnosisPayload(
		String descripcion,
		String estadoNutricional,
		String riesgosAsociados,
		String recomendaciones,
		String objetivo,
		String comentarios
	) {
			public DiagnosisPayload(Diagnosis d) {
					this(
							d.getDescription(),
							d.getNutritionalState().getLabel(),
							d.getAssociatedRisks(),
							d.getRecommendations(),
							d.getGoals(),
							d.getComments()
					);
			}
	}
}
