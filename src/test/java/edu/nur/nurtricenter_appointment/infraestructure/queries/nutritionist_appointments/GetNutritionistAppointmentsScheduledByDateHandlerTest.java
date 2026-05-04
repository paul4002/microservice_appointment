package edu.nur.nurtricenter_appointment.infraestructure.queries.nutritionist_appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist_appointments_scheduled_by_date.GetNutritionistAppointmentsScheduledByDateQuery;
import edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist_appointments_scheduled_by_date.ScheduledAppointmentNutritionistDto;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.persistence_model.AppointmentPersistenceModel;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.persistence_model.NutritionistPersistenceModel;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories.AppointmentCrudRepository;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories.NutritionistCrudRepository;

@ExtendWith(MockitoExtension.class)
public class GetNutritionistAppointmentsScheduledByDateHandlerTest {

	@Mock
	NutritionistCrudRepository nutritionistCrudRepository;

	@Mock
	AppointmentCrudRepository appointmentCrudRepository;

	@InjectMocks
	GetNutritionistAppointmentsScheduledByDate handler;

	private UUID nutritionistId;
	private LocalDate date;

	@BeforeEach
	void setUp() {
		nutritionistId = UUID.randomUUID();
		date = LocalDate.of(2026, 5, 10);
	}

	@Test
	void handle_WhenNutritionistNotFound_ShouldReturnFailure() {
		// Arrange
		when(nutritionistCrudRepository.findById(nutritionistId)).thenReturn(Optional.empty());
		GetNutritionistAppointmentsScheduledByDateQuery query =
				new GetNutritionistAppointmentsScheduledByDateQuery(nutritionistId, date);

		// Act
		ResultWithValue<List<ScheduledAppointmentNutritionistDto>> result = handler.handle(query);

		// Assert
		assertTrue(result.isFailure());
		assertEquals("Nutritionist.NotFound", result.getError().getCode());
	}

	@Test
	void handle_WhenNutritionistFoundWithNoAppointments_ShouldReturnEmptyList() {
		// Arrange
		NutritionistPersistenceModel nutritionist = new NutritionistPersistenceModel();
		nutritionist.setId(nutritionistId);
		when(nutritionistCrudRepository.findById(nutritionistId)).thenReturn(Optional.of(nutritionist));
		when(appointmentCrudRepository.findByNutritionistAndDate(nutritionistId, date)).thenReturn(List.of());
		GetNutritionistAppointmentsScheduledByDateQuery query =
				new GetNutritionistAppointmentsScheduledByDateQuery(nutritionistId, date);

		// Act
		ResultWithValue<List<ScheduledAppointmentNutritionistDto>> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		assertEquals(0, result.getValue().size());
	}

	@Test
	void handle_WhenNutritionistFoundWithAppointments_ShouldReturnMappedDtos() {
		// Arrange
		NutritionistPersistenceModel nutritionist = new NutritionistPersistenceModel();
		nutritionist.setId(nutritionistId);

		AppointmentPersistenceModel appt = new AppointmentPersistenceModel();
		appt.setId(UUID.randomUUID());
		appt.setPatientId(UUID.randomUUID());
		appt.setScheduledDate(LocalDateTime.of(2026, 5, 10, 10, 0, 0));
		appt.setType("Initial");
		appt.setStatus("Scheduled");
		appt.setAttendance("Pending");

		when(nutritionistCrudRepository.findById(nutritionistId)).thenReturn(Optional.of(nutritionist));
		when(appointmentCrudRepository.findByNutritionistAndDate(nutritionistId, date)).thenReturn(List.of(appt));
		GetNutritionistAppointmentsScheduledByDateQuery query =
				new GetNutritionistAppointmentsScheduledByDateQuery(nutritionistId, date);

		// Act
		ResultWithValue<List<ScheduledAppointmentNutritionistDto>> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertEquals(1, result.getValue().size());
		ScheduledAppointmentNutritionistDto dto = result.getValue().get(0);
		assertEquals(appt.getId().toString(), dto.id);
		assertEquals(appt.getPatientId().toString(), dto.patientId);
		assertEquals(appt.getType(), dto.type);
		assertEquals(appt.getStatus(), dto.status);
		assertEquals(appt.getAttendance(), dto.attendance);
	}
}
