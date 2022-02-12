# Pet-Store Implementation

As part of this applicaiton, exposed below APIs from Spring Boot applicaiton

1) Get All Pet Details

	This API is fetches all the Pet details from the database and return
	
	Controller: PetController
	Method Type : GET
	Response Type : List<Pet>
	Method URL : http://localhost:8080/petstore-app/v1/pets

2) Add Pet
	
	This API is adds a new Pet details to the database and return the saved entry
	
	Controller: PetController
	Method Type : POST
	Request Type : Pet object
	Response Type : Pet object
	Method URL : http://localhost:8080/petstore-app/v1/pets


3) Get Pet Details By Id

	This API is fetches the Pet details for the given PetId
	
	Controller: PetController
	Method Type : GET
	Request Type : Id
	Response Type : Pet object
	Method URL : http://localhost:8080/petstore-app/v1/pets/1

4) Delete Pet
	
	This API is deletes the Pet details for the given PetId
	
	Controller: PetController
	Method Type : DELETE
	Request Type : Id
	Method URL : http://localhost:8080/petstore-app/v1/pets/1
	
	