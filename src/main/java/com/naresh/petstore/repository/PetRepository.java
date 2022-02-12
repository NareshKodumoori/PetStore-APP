package com.naresh.petstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naresh.petstore.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
	
}