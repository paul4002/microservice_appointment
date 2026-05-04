package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model.NutritionistEntity;

@ExtendWith(MockitoExtension.class)
class NutritionistJpaRepositoryTest {

	@Mock
	private NutritionistEntityRepository nutritionistEntityRepository;

	@InjectMocks
	private NutritionistJpaRepository nutritionistJpaRepository;

	private UUID nutritionistId;
	private Nutritionist nutritionist;

	@BeforeEach
	void setUp() {
		nutritionistId = UUID.randomUUID();
		nutritionist = new Nutritionist(
				nutritionistId,
				"Juan",
				"Velasquez",
				NutritionistSpecialty.CLINICAL_NUTRITION,
				"LIC-123"
		);
	}

	@Test
	void shouldAddNutritionistAndReturnId() {
		// Arrange
		NutritionistEntity entity = NutritionistEntity.fromDomain(nutritionist);
		when(nutritionistEntityRepository.save(entity)).thenReturn(entity);

		// Act
		UUID result = nutritionistJpaRepository.Add(nutritionist);

		// Assert
		assertEquals(nutritionistId, result);
		verify(nutritionistEntityRepository).save(entity);
	}

	@Test
	void shouldDeleteNutritionistByDeactivating() {
		// Arrange
		NutritionistEntity entity = NutritionistEntity.fromDomain(nutritionist);

		// Act
		nutritionistJpaRepository.Delete(nutritionist);

		// Assert
		verify(nutritionistEntityRepository).deactivateById(entity.getId());
	}

	@Test
	void shouldGetNutritionistByIdWhenEntityExists() {
		// Arrange
		NutritionistEntity entity = NutritionistEntity.fromDomain(nutritionist);
		when(nutritionistEntityRepository.findById(nutritionistId)).thenReturn(Optional.of(entity));

		// Act
		Nutritionist result = nutritionistJpaRepository.GetById(nutritionistId);

		// Assert
		assertEquals(nutritionistId, result.getId());
		assertEquals(nutritionist.getName(), result.getName());
		assertEquals(nutritionist.getLastname(), result.getLastname());
		assertEquals(nutritionist.getSpecialty(), result.getSpecialty());
		assertEquals(nutritionist.getProfessionalLicense(), result.getProfessionalLicense());
	}

	@Test
	void shouldReturnNullWhenNutritionistNotFound() {
		// Arrange
		when(nutritionistEntityRepository.findById(nutritionistId)).thenReturn(Optional.empty());

		// Act
		Nutritionist result = nutritionistJpaRepository.GetById(nutritionistId);

		// Assert
		assertNull(result);
	}

	@Test
	void shouldUpdateNutritionistBySavingEntity() {
		// Arrange
		NutritionistEntity entity = NutritionistEntity.fromDomain(nutritionist);
		when(nutritionistEntityRepository.save(entity)).thenReturn(entity);

		// Act
		nutritionistJpaRepository.Update(nutritionist);

		// Assert
		verify(nutritionistEntityRepository).save(entity);
	}
}
