package com.naresh.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.naresh.petstore.exception.InvalidPetIdException;
import com.naresh.petstore.exception.PetAlreadyExistsException;
import com.naresh.petstore.model.Pet;
import com.naresh.petstore.repository.PetRepository;
import com.naresh.petstore.service.PetService;

@Service
@Transactional
public class PetServiceImpl implements PetService {

	private static final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

	@Autowired
	private PetRepository petRepository;

	@Override
	public Pet createPet(Pet petObj) throws Exception {
		Pet result = null;

		if (null != petObj) {
			Long petId = petObj.getId();
			if (petId == null || petId == 0) {
				Pet toBeSaved = new Pet();
				BeanUtils.copyProperties(petObj, toBeSaved);
				result = petRepository.save(toBeSaved);
			} else {
				logger.error("Id should be null or zero while creating the new Pet, but here we have the id as " + petId);
				throw new PetAlreadyExistsException("Id should be null or zero while creating the new Pet");
			}
		}
		logger.info("PetDetails are added Successfully");
		return result;
	}

	@Override
	public Pet getPetDetailsById(Long id) {
		logger.info("getPetDetails(id) method in PetServiceImpl for the id " + id);
		return petRepository.findById(id).orElseThrow(InvalidPetIdException::new);
	}

	@Override
	public List<Pet> getAllPets() {
		logger.info("getAllPets() method in PetServiceImpl");
		final List<Pet> petList = new ArrayList<>();
		List<Pet> all = petRepository.findAll();
		if(!CollectionUtils.isEmpty(all)){
			all.forEach(petList::add);
		}
		return petList;
	}

	@Override
	public void deletePetDetailsById(Long id) {

		if (petRepository.existsById(id)) {
			petRepository.deleteById(id);
			logger.info("Pet details are deleted successfully for the id " + id);
		} else {
			logger.info("We don't have Pet details for the given id " + id);
			throw new InvalidPetIdException("We don't have Pet details for the given id " + id);
		}
	}

}
