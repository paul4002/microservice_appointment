package edu.nur.nurtricenter_appointment.domain.appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;

public class UnitMeasureTest {

	@Test
	void constructor_ShouldStoreValueAndUnit() {
		// Arrange
		DecimalValue value = new DecimalValue(70.0);
		UnitMeasureName unit = UnitMeasureName.KG;

		// Act
		UnitMeasure unitMeasure = new UnitMeasure(value, unit);

		// Assert
		assertEquals(value, unitMeasure.value());
		assertEquals(unit, unitMeasure.unit());
	}

	@Test
	void constructor_WithHeightInCm_ShouldStoreCorrectUnit() {
		// Arrange
		DecimalValue value = new DecimalValue(170.0);
		UnitMeasureName unit = UnitMeasureName.CM;

		// Act
		UnitMeasure unitMeasure = new UnitMeasure(value, unit);

		// Assert
		assertEquals(value, unitMeasure.value());
		assertEquals(UnitMeasureName.CM, unitMeasure.unit());
	}
}
