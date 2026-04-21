package edu.nur.nurtricenter_appointment.domain.appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;

public class MeasurementTest {

	private DecimalValue weight;
	private DecimalValue height;
	private DecimalValue imc;
	private Percentage bodyFat;
	private Percentage muscleMass;
	private Measurement measurement;

	@BeforeEach
	void setUp() {
		weight = new DecimalValue(70.0);
		height = new DecimalValue(170.0);
		imc = new DecimalValue(24.2);
		bodyFat = new Percentage(new DecimalValue(18.5));
		muscleMass = new Percentage(new DecimalValue(40.0));
		measurement = new Measurement(weight, height, imc, bodyFat, muscleMass);
	}

	@Test
	void getWeight_ShouldReturnUnitMeasureWithKg() {
		// Arrange
		DecimalValue expectedValue = weight;

		// Act
		UnitMeasure result = measurement.getWeight();

		// Assert
		assertNotNull(result);
		assertEquals(expectedValue, result.value());
		assertEquals(UnitMeasureName.KG, result.unit());
	}

	@Test
	void getHeight_ShouldReturnUnitMeasureWithCm() {
		// Arrange
		DecimalValue expectedValue = height;

		// Act
		UnitMeasure result = measurement.getHeight();

		// Assert
		assertNotNull(result);
		assertEquals(expectedValue, result.value());
		assertEquals(UnitMeasureName.CM, result.unit());
	}

	@Test
	void getImc_ShouldReturnImcValue() {
		// Arrange
		DecimalValue expectedImc = imc;

		// Act
		DecimalValue result = measurement.getImc();

		// Assert
		assertEquals(expectedImc, result);
	}

	@Test
	void getBodyFat_ShouldReturnBodyFatPercentage() {
		// Arrange
		Percentage expectedBodyFat = bodyFat;

		// Act
		Percentage result = measurement.getBodyFat();

		// Assert
		assertEquals(expectedBodyFat, result);
	}

	@Test
	void getMuscleMass_ShouldReturnMuscleMassPercentage() {
		// Arrange
		Percentage expectedMuscleMass = muscleMass;

		// Act
		Percentage result = measurement.getMuscleMass();

		// Assert
		assertEquals(expectedMuscleMass, result);
	}

	@Test
	void defaultConstructor_ShouldCreateMeasurementWithNullFields() {
		// Arrange + Act
		Measurement emptyMeasurement = new Measurement();

		// Assert
		assertNotNull(emptyMeasurement);
		assertNull(emptyMeasurement.getWeight());
		assertNull(emptyMeasurement.getHeight());
	}
}
