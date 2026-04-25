package edu.nur.nurtricenter_appointment.application.appointments.attendAppointment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.application.utils.DiagnosisMapper;
import edu.nur.nurtricenter_appointment.application.utils.MeasurementMapper;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;
import edu.nur.nurtricenter_appointment.domain.appointments.events.AppointmentAttendedEvent;
import edu.nur.nurtricenter_appointment.infraestructure.config.UnitOfWorkJpa;
import edu.nur.nurtricenter_appointment.core.results.DomainException;
import edu.nur.nurtricenter_appointment.core.results.Error;

@Component
public class AttendAppointmentHandler implements Command.Handler<AttendAppointmentCommand, ResultWithValue<Boolean>> {
	private static final Logger logger = LoggerFactory.getLogger(AttendAppointmentHandler.class);
	private final IAppointmentRepository appointmentRepository;
	private final UnitOfWorkJpa unitOfWork;

	public AttendAppointmentHandler(IAppointmentRepository appointmentRepository, UnitOfWorkJpa unitOfWork) {
		this.appointmentRepository = appointmentRepository;
		this.unitOfWork = unitOfWork;
	}

	@Override
	public ResultWithValue<Boolean> handle(AttendAppointmentCommand request) {
		Appointment appointment = this.appointmentRepository.GetById(request.id());
		if (appointment == null)
			return ResultWithValue
					.failure(Error.notFound("Appointment.NotFound", "The appointment was not found", request.id().toString()));
		try {
			appointment.attend(
					request.notes(),
					MeasurementMapper.from(request.measurementDto()),
					DiagnosisMapper.from(request.diagnosisDto())
			// MealPlanMapper.from(request.mealPlanDto())
			);
		} catch (DomainException e) {
			Error err = e.getError();
			return ResultWithValue.failure(Error.failure(err.getCode(), err.getStructuredMessage(), request.id().toString()));
		}
		this.appointmentRepository.update(appointment);
		this.unitOfWork.commitAsync();
		AppointmentAttendedEvent event = new AppointmentAttendedEvent(appointment.getId(), appointment.getMeasurement(), appointment.getDiagnosis());
		appointment.addDomainEvent(event);
		logger.info("✓ Cita atendida - ID: {}, Evento: {}", appointment.getId(), event.getEventName());
		logger.info("✓ Evento publicado a RabbitMQ - Evento: {}", event.getEventName());
		return ResultWithValue.success(true);
	}
}
