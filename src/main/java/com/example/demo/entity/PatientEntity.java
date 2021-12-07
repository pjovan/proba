package com.example.demo.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient", uniqueConstraints = { @UniqueConstraint(columnNames = { "jmbg" }) })
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PatientEntity {

	@NotNull
	@Size(min = 13, max = 13)
	private String jmbg;

	@NotNull
	@Size(min = 2)
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@OneToMany(mappedBy = "patient", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, orphanRemoval = true)
	Set<ExaminationEntity> examinations = new HashSet<>();

	public PatientEntity(String jmbg, String name) {
		super();
		this.jmbg = jmbg;
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PatientEntity patient = (PatientEntity) o;
		return Objects.equals(jmbg, patient.jmbg);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbg);
	}
}
