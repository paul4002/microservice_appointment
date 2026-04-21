package edu.nur.nurtricenter_appointment.domain.patients;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;


public class PatientTest {
	@Test
	void shouldCreatePatientSuccessfully() {
		UUID id = UUID.randomUUID();
		Date birthDate = new Date();
		Email email = new Email("test@example.com");
		Cellphone cellphone = new Cellphone("77788899");

		Patient patient = new Patient(
						id,
						"Juan",
						"Torrez",
						birthDate,
						email,
						cellphone
		);

		assertEquals(id, patient.getId());
		assertEquals("Juan", patient.getName());
		assertEquals("Torrez", patient.getDocument());
		assertEquals(birthDate, patient.getBirthDate());
		assertEquals(email, patient.getEmail());
		assertEquals(cellphone, patient.getCellphone());
	}
}
