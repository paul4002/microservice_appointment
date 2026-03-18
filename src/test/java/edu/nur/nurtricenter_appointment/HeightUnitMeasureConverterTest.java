package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.appointments.UnitMeasure;
import edu.nur.nurtricenter_appointment.domain.appointments.UnitMeasureName;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.HeightUnitMeasureConverter;

public class HeightUnitMeasureConverterTest {
	private final HeightUnitMeasureConverter converter = new HeightUnitMeasureConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		UnitMeasure unitMeasure = new UnitMeasure(new DecimalValue(170.5), UnitMeasureName.CM);
		// Act
		Double result = converter.convertToDatabaseColumn(unitMeasure);
		// Assert
		assertEquals(170.5, result);
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
		Double dbValue = 180.0;
		// Act
		UnitMeasure result = converter.convertToEntityAttribute(dbValue);
		// Assert
		assertNotNull(result);
		assertEquals(180.0, result.value().value());
		assertEquals(UnitMeasureName.CM, result.unit());
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		UnitMeasure result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}
