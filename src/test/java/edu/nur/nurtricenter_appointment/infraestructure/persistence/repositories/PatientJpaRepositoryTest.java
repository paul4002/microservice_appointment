package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.nur.nurtricenter_appointment.domain.patients.Patient;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model.PatientEntity;

@ExtendWith(MockitoExtension.class)
class PatientJpaRepositoryTest {

	@Mock
	PatientEntityRepository patientEntityRepository;

	@InjectMocks
	PatientJpaRepository repository;

	private UUID patientId;
	private Patient patient;

	@BeforeEach
	void setUp() {
		patientId = UUID.randomUUID();
		patient = new Patient(patientId, "Carlos Ramírez", "1234567890");
	}

	@Test
	void getById_WhenEntityExists_ShouldReturnDomainPatient() {
		// Arrange
		PatientEntity entity = PatientEntity.fromDomain(patient);
		when(patientEntityRepository.findById(patientId)).thenReturn(Optional.of(entity));

		// Act
		Patient result = repository.GetById(patientId);

		// Assert
		assertEquals(patientId, result.getId());
		assertEquals(patient.getName(), result.getName());
	}

	@Test
	void getById_WhenEntityNotFound_ShouldReturnNull() {
		// Arrange
		when(patientEntityRepository.findById(patientId)).thenReturn(Optional.empty());

		// Act
		Patient result = repository.GetById(patientId);

		// Assert
		assertNull(result);
	}

	@Test
	void add_ShouldSaveEntityToRepository() {
		// Arrange
		when(patientEntityRepository.save(any(PatientEntity.class))).thenReturn(any(PatientEntity.class));

		// Act
		repository.Add(patient);

		// Assert
		verify(patientEntityRepository).save(any(PatientEntity.class));
	}
}
