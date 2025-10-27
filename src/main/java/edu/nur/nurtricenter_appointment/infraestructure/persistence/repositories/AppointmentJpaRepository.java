package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.AppointmentEntity;

@Repository
public class AppointmentJpaRepository implements IAppointmentRepository {
  @Autowired
  private AppointmentEntityRepository appointmentEntityRepository;

  @Override
  public boolean existsAppointmentNearTime(UUID nutritionistId, LocalDateTime start, LocalDateTime end) {
    return this.appointmentEntityRepository.existsAppointmentNearTime(nutritionistId, start, end);
  }

  @Override
  public UUID add(Appointment appointment) {
    AppointmentEntity entity = AppointmentEntity.fromDomain(appointment);
    this.appointmentEntityRepository.save(entity);
    return appointment.getId();
  }

  @Override
  public void update(Appointment appointment) {
    AppointmentEntity entity = AppointmentEntity.fromDomain(appointment);
    this.appointmentEntityRepository.save(entity);
  }

  @Override
  public Appointment GetById(UUID id) {
    Optional<AppointmentEntity> entity = this.appointmentEntityRepository.findById(id);
    return entity.map(AppointmentEntity::toDomain).orElse(null);
  }  
}
