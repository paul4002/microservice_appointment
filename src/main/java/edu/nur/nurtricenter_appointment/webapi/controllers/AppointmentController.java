package edu.nur.nurtricenter_appointment.webapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import an.awesome.pipelinr.Pipeline;
import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.AttendAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.cancelAppointment.CancelAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.notAttendedAppointment.NotAttendedAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment.ScheduleAppointmentCommand;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

import java.util.UUID;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
  private final  Pipeline pipeline;

  public AppointmentController(Pipeline pipeline) {
    this.pipeline = pipeline;
  }

  @PostMapping("/schedule")
  public ResultWithValue<UUID> scheduleAppointment(@RequestBody ScheduleAppointmentCommand command) {
    return command.execute(pipeline);
  }
  
  @PatchMapping("/cancel")
  public ResultWithValue<Boolean> cancelAppointment(@RequestBody CancelAppointmentCommand command) {
    return command.execute(pipeline);
  }

  @PatchMapping("/notattended")
  public ResultWithValue<Boolean> notAttendedAppointment(@RequestBody NotAttendedAppointmentCommand command) {
    return command.execute(pipeline);
  }

  @PostMapping("/attend")
  public ResultWithValue<Boolean> attend(@RequestBody AttendAppointmentCommand command) {
    return command.execute(pipeline);
  }
}
