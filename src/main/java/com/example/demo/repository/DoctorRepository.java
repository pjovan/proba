package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.HospitalEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

	public List<DoctorEntity> findByHospitals(HospitalEntity hospitals);

	public Optional<DoctorEntity> findByUsername(String username);

	public List<DoctorEntity> findAll();

}
