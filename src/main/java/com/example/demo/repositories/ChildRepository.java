package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ChildEntity;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

	List<ChildEntity> findByName(String string);

}
