package edu.nur.nurtricenter_appointment.webapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import an.awesome.pipelinr.Pipeline;
import edu.nur.nurtricenter_appointment.application.appointments.attend_appointment.AttendAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.cancel_appointment.CancelAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details.AppointmentDetailsDto;
import edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details.GetAppointmentDetailsQuery;
import edu.nur.nurtricenter_appointment.application.appointments.not_attended_appointment.NotAttendedAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.schedule_appointment.ScheduleAppointmentCommand;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

import java.util.UUID;

//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
	private final  Pipeline pipeline;

	public AppointmentController(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	// @PreAuthorize("hasRole('nutritionist')")
	@PostMapping("/schedule")
	public ResultWithValue<UUID> scheduleAppointment(@RequestBody ScheduleAppointmentCommand command) {
		return command.execute(pipeline);
	}

	// @PreAuthorize("hasRole('nutritionist')")
	@PatchMapping("/cancel")
	public ResultWithValue<Boolean> cancelAppointment(@RequestBody CancelAppointmentCommand command) {
		return command.execute(pipeline);
	}

	// @PreAuthorize("hasRole('nutritionist')")
	@PatchMapping("/notattended")
	public ResultWithValue<Boolean> notAttendedAppointment(@RequestBody NotAttendedAppointmentCommand command) {
		return command.execute(pipeline);
	}

	// @PreAuthorize("hasRole('nutritionist')")
	@PostMapping("/attend")
	public ResultWithValue<Boolean> attend(@RequestBody AttendAppointmentCommand command) {
		return command.execute(pipeline);
	}

	// @PreAuthorize("hasRole('nutritionist')")
	@GetMapping("/{appointmentId}")
	public ResultWithValue<AppointmentDetailsDto> getAppointmentDetails(@PathVariable UUID appointmentId) {
		return new GetAppointmentDetailsQuery(appointmentId).execute(pipeline);
	}
}
