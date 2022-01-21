package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.HospitalEntity;

@Repository
public interface HospitalRepozitory extends JpaRepository<HospitalEntity, Long> {

	Optional<HospitalEntity> findByName(String name);

	List<HospitalEntity> findAllByDoctorsIn(List<DoctorEntity> doctors);
}
