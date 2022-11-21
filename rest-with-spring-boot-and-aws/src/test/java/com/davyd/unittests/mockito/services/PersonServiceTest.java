package com.davyd.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.davyd.data.vo.PersonVO;
import com.davyd.exceptions.RequiredObjectIsNullException;
import com.davyd.models.Person;
import com.davyd.repositories.PersonRepository;
import com.davyd.services.PersonService;
import com.davyd.unittests.mapper.mock.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	MockPerson input;

	@InjectMocks
	private PersonService service;

	@Mock
	private PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() throws Exception {
		Person person = input.mockEntity(1);
		person.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(person));

		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test 1", result.getAddress());
		assertEquals("First Name Test 1", result.getFirstName());
		assertEquals("Last Name Test 1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testCreate() throws Exception {
		Person person = input.mockEntity(1);

		Person persisted = person;
		persisted.setId(1L);

		when(repository.save(person)).thenReturn(persisted);

		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);

		PersonVO result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test 1", result.getAddress());
		assertEquals("First Name Test 1", result.getFirstName());
		assertEquals("Last Name Test 1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testCreateWithNullPerson() throws Exception {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	void testUpdateWithNullPerson() throws Exception {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(expectedMessage.equals(actualMessage));
	}

	@Test
	void testUpdate() throws Exception {
		Person person = input.mockEntity(1);
		person.setId(1L);

		Person persisted = person;
		persisted.setId(1L);

		when(repository.save(person)).thenReturn(persisted);
		when(repository.findById(1L)).thenReturn(Optional.of(person));

		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);

		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test 1", result.getAddress());
		assertEquals("First Name Test 1", result.getFirstName());
		assertEquals("Last Name Test 1", result.getLastName());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testFindAll() throws Exception {
		List<Person> entityList = input.mockEntityList();

		when(repository.findAll()).thenReturn(entityList);

		List<PersonVO> people = service.findAll();

		assertNotNull(people);
		assertEquals(14, people.size());

		var personOne = people.get(1);
		assertNotNull(personOne);
		assertNotNull(personOne.getKey());
		assertNotNull(personOne.getLinks());
		assertTrue(personOne.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		assertEquals("Addres Test 1", personOne.getAddress());
		assertEquals("First Name Test 1", personOne.getFirstName());
		assertEquals("Last Name Test 1", personOne.getLastName());
		assertEquals("Female", personOne.getGender());

		var personFour = people.get(4);
		assertNotNull(personFour);
		assertNotNull(personFour.getKey());
		assertNotNull(personFour.getLinks());
		assertTrue(personFour.toString().contains("links: [</person/v1/4>;rel=\"self\"]"));
		assertEquals("Addres Test 4", personFour.getAddress());
		assertEquals("First Name Test 4", personFour.getFirstName());
		assertEquals("Last Name Test 4", personFour.getLastName());
		assertEquals("Male", personFour.getGender());

		var personSeven = people.get(7);
		assertNotNull(personSeven);
		assertNotNull(personSeven.getKey());
		assertNotNull(personSeven.getLinks());
		assertTrue(personSeven.toString().contains("links: [</person/v1/7>;rel=\"self\"]"));
		assertEquals("Addres Test 7", personSeven.getAddress());
		assertEquals("First Name Test 7", personSeven.getFirstName());
		assertEquals("Last Name Test 7", personSeven.getLastName());
		assertEquals("Female", personSeven.getGender());

	}

	@Test
	void testDelete() {
		Person person = input.mockEntity(1);
		person.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(person));

		service.delete(1L);
	}

}
