package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.NurseEntity;

@Repository
public interface NurseRepository extends JpaRepository<NurseEntity, Long> {

	public Optional<NurseEntity> findByUsername(String username);

}
