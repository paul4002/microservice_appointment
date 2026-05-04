package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentAttendance;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentStatus;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model.AppointmentEntity;

@ExtendWith(MockitoExtension.class)
public class AppointmentJpaRepositoryTest {

	@Mock
	AppointmentEntityRepository appointmentEntityRepository;

	@InjectMocks
	AppointmentJpaRepository repository;

	private UUID appointmentId;
	private Appointment appointment;

	@BeforeEach
	void setUp() {
		appointmentId = UUID.randomUUID();
		appointment = new Appointment(
				appointmentId,
				UUID.randomUUID(),
				UUID.randomUUID(),
				AppointmentType.INITIAL,
				LocalDateTime.now(),
				LocalDateTime.now().plusDays(1),
				null,
				AppointmentStatus.SCHEDULED,
				AppointmentAttendance.PENDING,
				"Consulta inicial");
	}

	@Test
	void add_ShouldSaveEntityAndReturnAppointmentId() {
		// Arrange
		AppointmentEntity entity = AppointmentEntity.fromDomain(appointment);
		when(appointmentEntityRepository.save(any(AppointmentEntity.class))).thenReturn(entity);

		// Act
		UUID result = repository.add(appointment);

		// Assert
		assertEquals(appointmentId, result);
		verify(appointmentEntityRepository).save(any(AppointmentEntity.class));
	}

	@Test
	void update_ShouldSaveUpdatedEntity() {
		// Arrange
		AppointmentEntity entity = AppointmentEntity.fromDomain(appointment);
		when(appointmentEntityRepository.save(any(AppointmentEntity.class))).thenReturn(entity);

		// Act
		repository.update(appointment);

		// Assert
		verify(appointmentEntityRepository).save(any(AppointmentEntity.class));
	}

	@Test
	void getById_WhenEntityExists_ShouldReturnDomainAppointment() {
		// Arrange
		AppointmentEntity entity = AppointmentEntity.fromDomain(appointment);
		when(appointmentEntityRepository.findById(appointmentId)).thenReturn(Optional.of(entity));

		// Act
		Appointment result = repository.GetById(appointmentId);

		// Assert
		assertEquals(appointmentId, result.getId());
		assertEquals(appointment.getPatientId(), result.getPatientId());
		assertEquals(appointment.getNutritionistId(), result.getNutritionistId());
	}

	@Test
	void getById_WhenEntityNotFound_ShouldReturnNull() {
		// Arrange
		when(appointmentEntityRepository.findById(appointmentId)).thenReturn(Optional.empty());

		// Act
		Appointment result = repository.GetById(appointmentId);

		// Assert
		assertNull(result);
	}

	@Test
	void existsAppointmentNearTime_ShouldDelegateToRepository() {
		// Arrange
		UUID nutritionistId = UUID.randomUUID();
		LocalDateTime start = LocalDateTime.now().plusDays(1);
		LocalDateTime end = start.plusHours(1);
		when(appointmentEntityRepository.existsAppointmentNearTime(nutritionistId, start, end)).thenReturn(true);

		// Act
		boolean result = repository.existsAppointmentNearTime(nutritionistId, start, end);

		// Assert
		assertEquals(true, result);
		verify(appointmentEntityRepository).existsAppointmentNearTime(nutritionistId, start, end);
	}
}
