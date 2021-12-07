package com.example.demo.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("1")
public class NurseEntity extends UserEntity {

	@ManyToOne
	@JoinColumn(name = "hospital_id")
	private HospitalEntity hospital;

	public NurseEntity() {
		super();
	}

	public NurseEntity(Long id, String username, String password, String name, HospitalEntity hospital,
			Boolean active) {
		super(id, username, password, name, active);
		this.hospital = hospital;
	}
}
