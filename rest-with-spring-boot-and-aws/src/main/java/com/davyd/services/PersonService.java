package com.davyd.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davyd.exceptions.ResourceNotFoundException;
import com.davyd.models.Person;
import com.davyd.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	public void delete(Long id) {
		logger.info("Deleting a person!");

		Person person = personRepository.findById(id).//
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		personRepository.delete(person);
	}

	public Person create(Person person) {
		logger.info("Creating a new person!");

		return personRepository.save(person);
	}

	public Person update(Person person) {
		logger.info("Updating person!");

		Person output = personRepository.findById(person.getId()).//
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		output.setAdress(person.getAdress());
		output.setFirstName(person.getFirstName());
		output.setLastName(person.getLastName());
		output.setGender(person.getGender());

		return personRepository.save(output);
	}

	public List<Person> findAll() {
		List<Person> people = personRepository.findAll();

		return people;
	}

	public Person findById(Long id) {
		logger.info("Finding one person!");

		Person person = personRepository.findById(id).//
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		return person;
	}

}
