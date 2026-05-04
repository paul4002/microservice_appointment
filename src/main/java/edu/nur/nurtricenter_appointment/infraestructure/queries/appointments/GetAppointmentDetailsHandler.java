package edu.nur.nurtricenter_appointment.infraestructure.queries.appointments;

import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details.AppointmentDetailsDto;
import edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details.GetAppointmentDetailsQuery;
import edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details.AppointmentDetailsDto.DiagnosisDetailsDto;
import edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details.AppointmentDetailsDto.MeasurementDetailsDto;
import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;

@Component
public class GetAppointmentDetailsHandler implements Command.Handler<GetAppointmentDetailsQuery, ResultWithValue<AppointmentDetailsDto>> {
	private final IAppointmentRepository appointmentRepository;

	public GetAppointmentDetailsHandler(IAppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public ResultWithValue<AppointmentDetailsDto> handle(GetAppointmentDetailsQuery request) {
		Appointment appointment = appointmentRepository.GetById(request.appointmentId());
		if (appointment == null)
			return ResultWithValue.failure(Error.notFound("Appointment.NotFound", "The appointment was not found", request.appointmentId().toString()));

		MeasurementDetailsDto measurementDto = null;
		if (appointment.getMeasurement() != null) {
			var measurement = appointment.getMeasurement();
			measurementDto = new MeasurementDetailsDto(
				new MeasurementDetailsDto.UnitMeasureDto(
					String.valueOf(measurement.getWeight().value().value()),
					measurement.getWeight().unit().toString()
				),
				new MeasurementDetailsDto.UnitMeasureDto(
					String.valueOf(measurement.getHeight().value().value()),
					measurement.getHeight().unit().toString()
				),
				measurement.getImc() != null ? String.valueOf(measurement.getImc().value()) : null,
				measurement.getBodyFat() != null ? String.valueOf(measurement.getBodyFat().value().value()) : null,
				measurement.getMuscleMass() != null ? String.valueOf(measurement.getMuscleMass().value().value()) : null
			);
		}

		DiagnosisDetailsDto diagnosisDto = null;
		if (appointment.getDiagnosis() != null) {
			Diagnosis diagnosis = appointment.getDiagnosis();
			diagnosisDto = new DiagnosisDetailsDto(
				diagnosis.getDescription(),
				diagnosis.getNutritionalState() != null ? diagnosis.getNutritionalState().toString() : null,
				diagnosis.getAssociatedRisks(),
				diagnosis.getRecommendations(),
				diagnosis.getGoals(),
				diagnosis.getComments()
			);
		}

		AppointmentDetailsDto dto = new AppointmentDetailsDto(
			appointment.getId().toString(),
			appointment.getPatientId().toString(),
			appointment.getNutritionistId().toString(),
			appointment.getType().getLabel(),
			appointment.getCreationDate(),
			appointment.getScheduledDate(),
			appointment.getCancelDate(),
			appointment.getStatus().getLabel(),
			appointment.getAttendance().toString(),
			measurementDto,
			diagnosisDto
		);

		return ResultWithValue.success(dto);
	}
}
