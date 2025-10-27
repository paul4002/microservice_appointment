package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.nur.nurtricenter_appointment.infraestructure.persistence.persistenceModel.AppointmentPersistenceModel;

public interface AppointmentCrudRepository extends CrudRepository<AppointmentPersistenceModel, UUID>  {
  @Query("""
      SELECT a
      FROM AppointmentPersistenceModel a
      WHERE a.nutritionistId = :nutritionistId
      AND DATE(a.scheduledDate) = :date
  """)
  List<AppointmentPersistenceModel> findByNutritionistAndDate(
    @Param("nutritionistId") UUID nutritionistId,
    @Param("date") LocalDate date
  );
}
