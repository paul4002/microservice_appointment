package edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist_appointments_scheduled_by_date;

import java.time.LocalDateTime;

public class ScheduledAppointmentNutritionistDto {
	public String id;
	public String patientId;
	public String type;
	public LocalDateTime scheduleDate;
	public String status;
	public String attendance;
}
