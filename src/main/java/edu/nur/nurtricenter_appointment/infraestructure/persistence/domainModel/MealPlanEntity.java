package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import edu.nur.nurtricenter_appointment.domain.meal.Meal;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "mealplans")
public class MealPlanEntity {

  @Id
  private UUID id;

  @Column(nullable = false)
  private String generalDescripcion;

  @Column(nullable = false)
  private String nutritionalGoal;

  private Date startDate;

  private Date endDate;

  private String restrictions;

  private UUID appointmentId;

  @OneToMany(
    mappedBy = "mealPlan",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<MealEntity> meals = new ArrayList<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getGeneralDescripcion() {
    return generalDescripcion;
  }

  public void setGeneralDescripcion(String generalDescripcion) {
    this.generalDescripcion = generalDescripcion;
  }

  public String getNutritionalGoal() {
    return nutritionalGoal;
  }

  public void setNutritionalGoal(String nutritionalGoal) {
    this.nutritionalGoal = nutritionalGoal;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getRestrictions() {
    return restrictions;
  }

  public void setRestrictions(String restrictions) {
    this.restrictions = restrictions;
  }

  public UUID getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(UUID appointmentId) {
    this.appointmentId = appointmentId;
  }

  public List<MealEntity> getMeals() {
    return meals;
  }

  public void setMeals(List<MealEntity> meals) {
    this.meals = meals;
  }

  public static MealPlanEntity fromDomain(MealPlan mealPlan) {
    MealPlanEntity mealPlanEntity = new MealPlanEntity();
    mealPlanEntity.id = mealPlan.getId();
    mealPlanEntity.generalDescripcion = mealPlan.getGeneralDescription();
    mealPlanEntity.nutritionalGoal = mealPlan.getNutritionalGoal();
    mealPlanEntity.startDate = mealPlan.getStartDate();
    mealPlanEntity.endDate = mealPlan.getEndDate();
    mealPlanEntity.restrictions = mealPlan.getRestrictions();
    mealPlanEntity.appointmentId = mealPlan.getAppointmentId();
    if (mealPlan.get_meals() != null) {
      List<MealEntity> mealEntities = new LinkedList<>();
      for(Meal meal : mealPlan.get_meals()) {
        MealEntity mealEntity = MealEntity.fromDomain(meal);
        mealEntity.setMealPlan(mealPlanEntity);
        mealEntities.add(mealEntity);
      }
      mealPlanEntity.meals = mealEntities;
    }
    return mealPlanEntity;
  }
}
