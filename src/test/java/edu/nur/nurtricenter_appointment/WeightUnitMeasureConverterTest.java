package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.appointments.UnitMeasure;
import edu.nur.nurtricenter_appointment.domain.appointments.UnitMeasureName;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.WeightUnitMeasureConverter;

public class WeightUnitMeasureConverterTest {
	private final WeightUnitMeasureConverter converter = new WeightUnitMeasureConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		UnitMeasure unitMeasure = new UnitMeasure(new DecimalValue(72.3), UnitMeasureName.KG);
		// Act
		Double result = converter.convertToDatabaseColumn(unitMeasure);
		// Assert
		assertEquals(72.3, result);
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
		Double dbValue = 85.0;
		// Act
		UnitMeasure result = converter.convertToEntityAttribute(dbValue);
		// Assert
		assertNotNull(result);
		assertEquals(85.0, result.value().value());
		assertEquals(UnitMeasureName.KG, result.unit());
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		UnitMeasure result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}
