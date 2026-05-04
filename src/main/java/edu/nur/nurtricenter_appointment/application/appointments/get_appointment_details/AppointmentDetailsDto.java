package edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details;

import java.time.LocalDateTime;

public class AppointmentDetailsDto {
	public String id;
	public String patientId;
	public String nutritionistId;
	public String type;
	public LocalDateTime creationDate;
	public LocalDateTime scheduleDate;
	public LocalDateTime cancelDate;
	public String status;
	public String attendance;
	public MeasurementDetailsDto measurement;
	public DiagnosisDetailsDto diagnosis;

	public AppointmentDetailsDto() {
	}

	public AppointmentDetailsDto(String id, String patientId, String nutritionistId, String type,
			LocalDateTime creationDate, LocalDateTime scheduleDate, LocalDateTime cancelDate, String status,
			String attendance, MeasurementDetailsDto measurement, DiagnosisDetailsDto diagnosis) {
		this.id = id;
		this.patientId = patientId;
		this.nutritionistId = nutritionistId;
		this.type = type;
		this.creationDate = creationDate;
		this.scheduleDate = scheduleDate;
		this.cancelDate = cancelDate;
		this.status = status;
		this.attendance = attendance;
		this.measurement = measurement;
		this.diagnosis = diagnosis;
	}

	public static class MeasurementDetailsDto {
		public UnitMeasureDto weight;
		public UnitMeasureDto height;
		public String imc;
		public String bodyFat;
		public String muscleMass;

		public MeasurementDetailsDto() {
		}

		public MeasurementDetailsDto(UnitMeasureDto weight, UnitMeasureDto height, String imc, String bodyFat,
				String muscleMass) {
			this.weight = weight;
			this.height = height;
			this.imc = imc;
			this.bodyFat = bodyFat;
			this.muscleMass = muscleMass;
		}

		public static class UnitMeasureDto {
			public String value;
			public String unit;

			public UnitMeasureDto() {
			}

			public UnitMeasureDto(String value, String unit) {
				this.value = value;
				this.unit = unit;
			}
		}
	}

	public static class DiagnosisDetailsDto {
		public String description;
		public String nutritionalState;
		public String associatedRisks;
		public String recommendations;
		public String goals;
		public String comments;

		public DiagnosisDetailsDto() {
		}

		public DiagnosisDetailsDto(String description, String nutritionalState, String associatedRisks,
				String recommendations, String goals, String comments) {
			this.description = description;
			this.nutritionalState = nutritionalState;
			this.associatedRisks = associatedRisks;
			this.recommendations = recommendations;
			this.goals = goals;
			this.comments = comments;
		}
	}
}
