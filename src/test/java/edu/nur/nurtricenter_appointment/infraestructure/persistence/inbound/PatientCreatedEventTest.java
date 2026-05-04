package edu.nur.nurtricenter_appointment.infraestructure.persistence.inbound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientCreatedEventTest {

	private PatientCreatedEvent event;

	@BeforeEach
	void setUp() {
		event = new PatientCreatedEvent();
	}

	@Test
	void setAndGetPacienteId_ShouldStoreValue() {
		// Arrange
		UUID pacienteId = UUID.randomUUID();

		// Act
		event.setPacienteId(pacienteId);

		// Assert
		assertEquals(pacienteId, event.getPacienteId());
	}

	@Test
	void setAndGetNombre_ShouldStoreValue() {
		// Arrange
		String nombre = "Carlos Ramírez";

		// Act
		event.setNombre(nombre);

		// Assert
		assertEquals(nombre, event.getNombre());
	}

	@Test
	void setAndGetDocumento_ShouldStoreValue() {
		// Arrange
		String documento = "1234567890";

		// Act
		event.setDocumento(documento);

		// Assert
		assertEquals(documento, event.getDocumento());
	}

	@Test
	void setAndGetSuscripcionId_ShouldStoreValue() {
		// Arrange
		UUID suscripcionId = UUID.randomUUID();

		// Act
		event.setSuscripcionId(suscripcionId);

		// Assert
		assertEquals(suscripcionId, event.getSuscripcionId());
	}
}
