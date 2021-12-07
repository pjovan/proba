package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.SpecializationEntity;

@Repository
public interface SpecializationRepository extends JpaRepository<SpecializationEntity, Integer> {

	Optional<SpecializationEntity> findByName(String name);
}
