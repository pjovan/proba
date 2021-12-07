package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PatientEntity;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

	public Optional<PatientEntity> findByJmbg(String jmbg);

}
