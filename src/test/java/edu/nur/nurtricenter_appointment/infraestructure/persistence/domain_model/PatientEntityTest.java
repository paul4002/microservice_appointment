package edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.patients.Cellphone;
import edu.nur.nurtricenter_appointment.domain.patients.Email;
import edu.nur.nurtricenter_appointment.domain.patients.Patient;

import static org.junit.jupiter.api.Assertions.assertNull;

public class PatientEntityTest {
	@Test
	void shouldSetAndGetFields() {
		// Arrange
		UUID id = UUID.randomUUID();
		String name = "Luis";
		String lastname = "Padilla";
		Date birthDate = new Date();
		Email email = new Email("test@email.com");
		Cellphone cellphone = new Cellphone("71234678");

		// Act
		PatientEntity entity = new PatientEntity();
		entity.setId(id);
		entity.setName(name);
		entity.setDocument(lastname);
		entity.setBirthDate(birthDate);
		entity.setEmail(email);
		entity.setCellphone(cellphone);

		// Assert
		assertEquals(id, entity.getId());
		assertEquals(name, entity.getName());
		assertEquals(lastname, entity.getDocument());
		assertEquals(birthDate, entity.getBirthDate());
		assertEquals(email.value(), entity.getEmail().value());
		assertEquals(cellphone.value(), entity.getCellphone().value());
	}

	@Test
	void shouldConvertFromDomain() {
		// Arrange
		UUID id = UUID.randomUUID();
		Patient patient = new Patient(id, "Luis", "Padilla", new Date(),
				new Email("test@email.com"), new Cellphone("71238421"));

		// Act
		PatientEntity entity = PatientEntity.fromDomain(patient);

		// Assert
		assertEquals(patient.getId(), entity.getId());
		assertEquals(patient.getName(), entity.getName());
		assertEquals(patient.getDocument(), entity.getDocument());
		assertNull(entity.getBirthDate());
		assertNull(entity.getEmail());
		assertNull(entity.getCellphone());
	}

	@Test
	void shouldConvertToDomain() {
		// Arrange
		PatientEntity entity = new PatientEntity();
		entity.setId(UUID.randomUUID());
		entity.setName("Luis");
		entity.setDocument("Padilla");
		entity.setBirthDate(new Date());
		entity.setEmail(new Email("test@email.com"));
		entity.setCellphone(new Cellphone("71238421"));

		// Act
		Patient patient = entity.toDomain();

		// Assert
		assertEquals(entity.getId(), patient.getId());
		assertEquals(entity.getName(), patient.getName());
		assertEquals(entity.getDocument(), patient.getDocument());
		assertEquals(entity.getBirthDate(), patient.getBirthDate());
		assertEquals(entity.getEmail().value(), patient.getEmail().value());
		assertEquals(entity.getCellphone().value(), patient.getCellphone().value());
	}
}
