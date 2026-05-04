package edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model;

import java.util.Objects;
import java.util.UUID;

import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model.converters.NutritionistSpecialtyConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nutritionists")
public class NutritionistEntity {
	@Id
	private UUID id;
	private String name;
	private String lastname;
	@Convert(converter = NutritionistSpecialtyConverter.class)
	private NutritionistSpecialty specialty;
	private String professionalLicense;
	@Column(columnDefinition = "boolean default true")
	private Boolean state;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public NutritionistSpecialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(NutritionistSpecialty specialty) {
		this.specialty = specialty;
	}
	public String getProfessionalLicense() {
		return professionalLicense;
	}
	public void setProfessionalLicense(String professionalLicense) {
		this.professionalLicense = professionalLicense;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}

	public static NutritionistEntity fromDomain(Nutritionist nutritionist) {
		NutritionistEntity nutritionistEntity = new NutritionistEntity();
		nutritionistEntity.id = nutritionist.getId();
		nutritionistEntity.name = nutritionist.getName();
		nutritionistEntity.lastname = nutritionist.getLastname();
		nutritionistEntity.specialty = nutritionist.getSpecialty();
		nutritionistEntity.professionalLicense = nutritionist.getProfessionalLicense();
		nutritionistEntity.state = true;
		return nutritionistEntity;
	}

	public Nutritionist toDomain() {
		return new Nutritionist(id, name, lastname, specialty, professionalLicense);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof NutritionistEntity other)) return false;
		return Objects.equals(id, other.id)
			&& Objects.equals(name, other.name)
			&& Objects.equals(lastname, other.lastname)
			&& Objects.equals(specialty, other.specialty)
			&& Objects.equals(professionalLicense, other.professionalLicense)
			&& Objects.equals(state, other.state);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, lastname, specialty, professionalLicense, state);
	}
}
