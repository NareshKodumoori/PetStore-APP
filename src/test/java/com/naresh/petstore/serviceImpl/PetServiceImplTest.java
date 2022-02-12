package com.naresh.petstore.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.naresh.petstore.exception.InvalidPetIdException;
import com.naresh.petstore.exception.PetAlreadyExistsException;
import com.naresh.petstore.model.Pet;
import com.naresh.petstore.repository.PetRepository;
import com.naresh.petstore.service.impl.PetServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PetServiceImplTest {

	@InjectMocks
	PetServiceImpl petService;

	@Mock
	PetRepository petRepository;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createPet_WithNullPetTest() throws Exception {
		Pet result = petService.createPet(null);
		Assert.assertEquals(null, result);
	}

	@Test(expected = PetAlreadyExistsException.class)
	public void createPet_WithPetIdTest() throws Exception {
		Pet petObj = new Pet();
		petObj.setId(1L);
		petService.createPet(petObj);
	}

	@Test
	public void createPetTest() throws Exception {
		Pet petObj = new Pet();
		petObj.setPrice(100.00);
		petObj.setType("AtHome");
		Mockito.when(petRepository.save(petObj)).thenReturn(petObj);
		petService.createPet(petObj);
	}

	@Test
	public void getAllPetsTest() {
		List<Pet> list = new ArrayList<>();

		Pet petObj1 = new Pet();
		petObj1.setId(1L);
		petObj1.setPrice(100.00);
		petObj1.setType("AtHome");

		Pet petObj2 = new Pet();
		petObj2.setId(2L);
		petObj2.setPrice(200.00);
		petObj2.setType("OutSide");

		list.add(petObj1);
		list.add(petObj2);

		Mockito.when(petRepository.findAll()).thenReturn(list);

		List<Pet> result = petService.getAllPets();

		Assert.assertEquals(3, result.size());
	}

	@Test
	public void getAllPets_WithNoDataTest() {
		Mockito.when(petRepository.findAll()).thenReturn(null);
		List<Pet> result = petService.getAllPets();
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void getPetDetailsByIdTest() {
		Pet petObj = new Pet();
		petObj.setId(1L);
		petObj.setPrice(100.00);
		petObj.setType("AtHome");

		Mockito.when(petRepository.findById(1L)).thenReturn(Optional.of(petObj));
		Pet result = petService.getPetDetailsById(1L);
		long id = result.getId();
		Assert.assertEquals(1, id);
	}

	@Test(expected = InvalidPetIdException.class)
	public void getPetDetailsById_WithNoDataTest() {
		Mockito.when(petRepository.findById(1L)).thenReturn(null);
		petService.getPetDetailsById(1L);
	}

	@Test
	public void deletePetDetailsByIdTest() {
		Mockito.when(petRepository.existsById(1L)).thenReturn(Boolean.TRUE);
		petService.deletePetDetailsById(1L);
	}

	@Test(expected = InvalidPetIdException.class)
	public void deletePetDetailsById_WithNoDataTest() {
		Mockito.when(petRepository.existsById(1L)).thenReturn(Boolean.FALSE);
		petService.deletePetDetailsById(1L);
	}

}
