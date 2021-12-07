package com.example.demo.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hospital")
public class HospitalEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String address;
	private String name;

	@ManyToMany(mappedBy = "hospitals", cascade = CascadeType.ALL)
	Set<DoctorEntity> doctors = new HashSet<>();

	@OneToMany(mappedBy = "hospital")
	private Set<NurseEntity> nurse;

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		HospitalEntity hospital = (HospitalEntity) object;
		return Objects.equals(id, hospital.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
