package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.DecimalValueConverter;

public class DecimalValueConverterTest {
  private final DecimalValueConverter converter = new DecimalValueConverter();

  @Test
  void shouldConvertToDatabaseColumn() {
    // Arrange
    DecimalValue decimalValue = new DecimalValue(12.75);
    // Act
    Double result = converter.convertToDatabaseColumn(decimalValue);
    // Assert
    assertEquals(12.75, result);
  }

  @Test
  void shouldReturnNullWhenConvertToDatabaseColumnWithNull() {
    // Act
    Double result = converter.convertToDatabaseColumn(null);
    // Assert
    assertNull(result);
  }

  @Test
  void shouldConvertToEntityAttribute() {
    // Arrange
    Double dbValue = 18.5;
    // Act
    DecimalValue result = converter.convertToEntityAttribute(dbValue);
    // Assert
    assertNotNull(result);
    assertEquals(18.5, result.value());
  }

  @Test
  void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
    // Act
    DecimalValue result = converter.convertToEntityAttribute(null);
    // Assert
    assertNull(result);
  }
}
