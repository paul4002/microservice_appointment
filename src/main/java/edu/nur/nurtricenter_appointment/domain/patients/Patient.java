package edu.nur.nurtricenter_appointment.domain.patients;

import java.util.Date;
import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.AggregateRoot;

public class Patient extends AggregateRoot {
  private String name;
  private String lastname;
  private Date birthDate;
  private Email email;
  private Cellphone cellphone;
  
  public Patient(UUID id, String name, String lastname, Date birthDate, Email email, Cellphone cellphone) {
    super(id);
    this.name = name;
    this.lastname = lastname;
    this.birthDate = birthDate;
    this.email = email;
    this.cellphone = cellphone;
  }

  public String getName() {
    return name;
  }
  public String getLastname() {
    return lastname;
  }
  public Date getBirthDate() {
    return birthDate;
  }
  public Email getEmail() {
    return email;
  }
  public Cellphone getCellphone() {
    return cellphone;
  }
}
