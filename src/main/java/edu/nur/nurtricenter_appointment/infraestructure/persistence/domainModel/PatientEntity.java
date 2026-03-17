package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel;

import java.util.Date;
import java.util.UUID;

import edu.nur.nurtricenter_appointment.domain.patients.Cellphone;
import edu.nur.nurtricenter_appointment.domain.patients.Email;
import edu.nur.nurtricenter_appointment.domain.patients.Patient;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.CellphoneConverter;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.EmailConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class PatientEntity {
	@Id
	private UUID id;
	private String name;
	private String document;
	private Date birthDate;
	@Convert(converter = EmailConverter.class)
	private Email email;
	@Convert(converter = CellphoneConverter.class)
	private Cellphone cellphone;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String lastname) {
		this.document = lastname;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public Cellphone getCellphone() {
		return cellphone;
	}
	public void setCellphone(Cellphone cellphone) {
		this.cellphone = cellphone;
	}

	public static PatientEntity fromDomain(Patient patient) {
		PatientEntity entity = new PatientEntity();
		entity.id = patient.getId();
		entity.name = patient.getName();
		entity.document = patient.getDocument();
		return entity;
	}
	
	public Patient toDomain() {
		return new Patient(id, name, document, birthDate, email, cellphone);
	}
}
