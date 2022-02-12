package com.naresh.petstore.service;

import java.util.List;

import com.naresh.petstore.model.Pet;

public interface PetService {
	
	public Pet createPet(Pet petObj) throws Exception;
	
	public Pet getPetDetailsById(Long pedId);
	
	public List<Pet> getAllPets();
	
	public void deletePetDetailsById(Long pedId);
	
}
