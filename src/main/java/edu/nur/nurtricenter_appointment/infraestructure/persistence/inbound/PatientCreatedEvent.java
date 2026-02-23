package edu.nur.nurtricenter_appointment.infraestructure.persistence.inbound;

import java.util.UUID;

public class PatientCreatedEvent {
  private UUID pacienteId;
  private String nombre;
  private String documento;
  private UUID suscripcionId;

  public UUID getPacienteId() {
    return pacienteId;
  }
  public void setPacienteId(UUID pacienteId) {
    this.pacienteId = pacienteId;
  }
  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public String getDocumento() {
    return documento;
  }
  public void setDocumento(String documento) {
    this.documento = documento;
  }
  public UUID getSuscripcionId() {
    return suscripcionId;
  }
  public void setSuscripcionId(UUID suscripcionId) {
    this.suscripcionId = suscripcionId;
  }

  
}
