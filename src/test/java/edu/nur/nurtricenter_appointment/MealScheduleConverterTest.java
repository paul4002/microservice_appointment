package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.meal.MealSchedule;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.MealScheduleConverter;

public class MealScheduleConverterTest {
  private final MealScheduleConverter converter = new MealScheduleConverter();

  @Test
  void shouldConvertToDatabaseColumn() {
    // Arrange
    MealSchedule schedule = MealSchedule.BREAKFAST;
    // Act
    String result = converter.convertToDatabaseColumn(schedule);
    // Assert
    assertEquals(schedule.getLabel(), result);
  }

  @Test
  void shouldReturnNullWhenConvertToDatabaseColumnWithNull() {
    // Act
    String result = converter.convertToDatabaseColumn(null);
    // Assert
    assertNull(result);
  }

  @Test
  void shouldConvertToEntityAttribute() {
    // Arrange
    String label = MealSchedule.LUNCH.getLabel();
    // Act
    MealSchedule result = converter.convertToEntityAttribute(label);
    // Assert
    assertNotNull(result);
    assertEquals(MealSchedule.LUNCH, result);
  }

  @Test
  void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
    // Act
    MealSchedule result = converter.convertToEntityAttribute(null);
    // Assert
    assertNull(result);
  }
}
