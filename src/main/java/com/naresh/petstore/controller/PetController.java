package com.naresh.petstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naresh.petstore.model.Pet;
import com.naresh.petstore.service.PetService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/pets")
public class PetController {

	private static final Logger logger = LoggerFactory.getLogger(PetController.class);

	@Autowired
	private PetService petService;

	@PostMapping()
	@ApiOperation("This API creates a Pet details")
	ResponseEntity<Pet> createPet(@RequestBody(required = true) @Valid Pet petObj) throws Exception {
		logger.info("createPet() method in PetController");
		
		Pet result = petService.createPet(petObj);
		logger.info("created teh Pet details successfully");
		return ResponseEntity.ok().body(result);
	}

	@GetMapping()
	@ApiOperation("This API fetches all the of Pets information")
	ResponseEntity<List<Pet>> getAllPets() {
		logger.info("getAllPets() method in PetController");
		List<Pet> petList = petService.getAllPets();
		return ResponseEntity.ok().body(petList);
	}

	@GetMapping("/{id}")
	@ApiOperation("This API fetches the given Id's Pets information")
	@HystrixCommand(fallbackMethod = "fallbackMethod")
	ResponseEntity<Pet> getPet(@PathVariable Long id) {
		logger.info("getPet(id) method in PetController with the id " + id);

		Pet result = petService.getPetDetailsById(id);
		if (result == null) {
			logger.info("We don't have Pet details for the id " + id);
			return ResponseEntity.notFound().build();
		} else {
			logger.info("Successfully fetched the Pet details for the id " + id);
			return ResponseEntity.ok().body(result);
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation("This API deletes the given Id's Pet")
	@HystrixCommand(fallbackMethod = "fallbackMethod")
	ResponseEntity<Void> deletePet(@PathVariable Long id) {
		petService.deletePetDetailsById(id);
		return ResponseEntity.ok().build();
	}

	public String fallbackMethod(Long petId) {
		return "Fallback response:: No Pet details available temporarily";
	}

}
