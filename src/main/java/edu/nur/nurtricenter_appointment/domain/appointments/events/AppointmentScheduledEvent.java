package edu.nur.nurtricenter_appointment.domain.appointments.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.DomainEvent;

public class AppointmentScheduledEvent extends DomainEvent {
	private final UUID appointmentId;
	private final UUID patientId;
	private final LocalDateTime scheduledDate;

	public AppointmentScheduledEvent(UUID appointmentId, UUID patientId, LocalDateTime scheduledDate) {
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.scheduledDate = scheduledDate;
	}

	public UUID getAppointmentId() {
		return appointmentId;
	}

	public UUID getPatientId() {
		return patientId;
	}

	public LocalDateTime getScheduledDate() {
		return scheduledDate;
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
		return "citas-evaluaciones.cita.agendada";
	}

	@Override
	public Object getPayload() {
		return new Payload(appointmentId.toString(), patientId.toString(), scheduledDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
	}

	private record Payload(String citaId, String pacienteId, String horarioAgendado) {}
}
