package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.ExaminationEntity;
import com.example.demo.entity.ExaminationId;

@Repository
public interface ExaminationRepository extends JpaRepository<ExaminationEntity, ExaminationId> {

	List<ExaminationEntity> findByDoctor(DoctorEntity doctor);
}
