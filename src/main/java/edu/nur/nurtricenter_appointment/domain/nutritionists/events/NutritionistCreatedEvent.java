package edu.nur.nurtricenter_appointment.domain.nutritionists.events;

import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.DomainEvent;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;

public class NutritionistCreatedEvent extends DomainEvent {
	private final UUID nutricionistaId;
	private final String nombre;
	private final String apellido;
	private final NutritionistSpecialty especialidad;
	private final String licenciaProfesional;
	
	public NutritionistCreatedEvent(UUID nutricionistaId, String nombre, String apellido, NutritionistSpecialty especialidad,
			String licenciaProfesional) {
		this.nutricionistaId = nutricionistaId;
		this.nombre = nombre;
		this.apellido = apellido;
		this.especialidad = especialidad;
		this.licenciaProfesional = licenciaProfesional;
	}

	public UUID getNutricionistaId() {
		return nutricionistaId;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public NutritionistSpecialty getEspecialidad() {
		return especialidad;
	}

	public String getLicenciaProfesional() {
		return licenciaProfesional;
	}

	@Override
	public String getAggregateId() {
		return nutricionistaId.toString();
	}

	@Override
	public String getAggregateType() {
		return "Nutritionist";
	}

	@Override
	public String getEventName() {
		return "citas-evaluaciones.nutricionista.creado";
	}

	@Override
	public Object getPayload() {
		return new Payload(apellido, nombre, apellido, especialidad.getLabel(), licenciaProfesional);
	}

	private record Payload(String nutricionistaId, String nombre, String apellido, String especialidad, String licenciaProfesional) {}
}
