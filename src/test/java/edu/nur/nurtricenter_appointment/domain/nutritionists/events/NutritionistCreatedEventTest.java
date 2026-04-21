package edu.nur.nurtricenter_appointment.domain.nutritionists.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;

public class NutritionistCreatedEventTest {

	private UUID nutricionistaId;
	private String nombre;
	private String apellido;
	private NutritionistSpecialty especialidad;
	private String licenciaProfesional;
	private NutritionistCreatedEvent event;

	@BeforeEach
	void setUp() {
		nutricionistaId = UUID.randomUUID();
		nombre = "Carlos";
		apellido = "Ramírez";
		especialidad = NutritionistSpecialty.CLINICAL_NUTRITION;
		licenciaProfesional = "LIC-2024-001";
		event = new NutritionistCreatedEvent(nutricionistaId, nombre, apellido, especialidad, licenciaProfesional);
	}

	@Test
	void getNutricionistaId_ShouldReturnNutricionistaId() {
		// Arrange
		UUID expectedId = nutricionistaId;

		// Act
		UUID result = event.getNutricionistaId();

		// Assert
		assertEquals(expectedId, result);
	}

	@Test
	void getNombre_ShouldReturnNombre() {
		// Arrange
		String expectedNombre = nombre;

		// Act
		String result = event.getNombre();

		// Assert
		assertEquals(expectedNombre, result);
	}

	@Test
	void getApellido_ShouldReturnApellido() {
		// Arrange
		String expectedApellido = apellido;

		// Act
		String result = event.getApellido();

		// Assert
		assertEquals(expectedApellido, result);
	}

	@Test
	void getEspecialidad_ShouldReturnEspecialidad() {
		// Arrange
		NutritionistSpecialty expectedEspecialidad = especialidad;

		// Act
		NutritionistSpecialty result = event.getEspecialidad();

		// Assert
		assertEquals(expectedEspecialidad, result);
	}

	@Test
	void getLicenciaProfesional_ShouldReturnLicenciaProfesional() {
		// Arrange
		String expectedLicencia = licenciaProfesional;

		// Act
		String result = event.getLicenciaProfesional();

		// Assert
		assertEquals(expectedLicencia, result);
	}

	@Test
	void getAggregateId_ShouldReturnNutricionistaIdAsString() {
		// Arrange
		String expectedAggregateId = nutricionistaId.toString();

		// Act
		String result = event.getAggregateId();

		// Assert
		assertEquals(expectedAggregateId, result);
	}

	@Test
	void getAggregateType_ShouldReturnNutritionist() {
		// Arrange
		String expectedType = "Nutritionist";

		// Act
		String result = event.getAggregateType();

		// Assert
		assertEquals(expectedType, result);
	}

	@Test
	void getEventName_ShouldReturnEventName() {
		// Arrange
		String expectedEventName = "citas-evaluaciones.nutricionista.creado";

		// Act
		String result = event.getEventName();

		// Assert
		assertEquals(expectedEventName, result);
	}

	@Test
	void getPayload_ShouldReturnNonNullPayload() {
		// Arrange + Act
		Object result = event.getPayload();

		// Assert
		assertNotNull(result);
	}

	@Test
	void getPayload_ShouldContainEspecialidadLabel() {
		// Arrange
		String expectedLabel = especialidad.getLabel();

		// Act
		Object payload = event.getPayload();
		String payloadString = payload.toString();

		// Assert
		assertNotNull(payload);
		assertEquals(true, payloadString.contains(expectedLabel));
	}

	@Test
	void getPayload_ShouldContainLicenciaProfesional() {
		// Arrange
		String expectedLicencia = licenciaProfesional;

		// Act
		Object payload = event.getPayload();
		String payloadString = payload.toString();

		// Assert
		assertNotNull(payload);
		assertEquals(true, payloadString.contains(expectedLicencia));
	}
}
