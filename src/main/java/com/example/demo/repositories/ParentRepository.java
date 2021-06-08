package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ParentEntity;

@Repository
public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

	Optional<ParentEntity> findByName(String name);

}
