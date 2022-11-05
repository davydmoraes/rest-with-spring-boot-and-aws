package com.davyd.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.davyd.models.Person;

@Service
public class PersonService {
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonService.class.getName());

	public void delete(String id) {
		logger.info("Deleting a person!");
	}
	
	public Person create(Person person) {
		logger.info("Creating a new person!");
		return person;
	}

	public Person update(Person person) {
		logger.info("Updating person!");
		return person;
	}

	public List<Person> findAll() {
		List<Person> people = new ArrayList<Person>();

		for (int i = 0; i < 9; i++) {
			Person person = mockPerson(i);
			people.add(person);
		}

		return people;
	}

	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(i);
		person.setAdress("Street number " + i);
		person.setFirstName("Person number" + i);
		person.setLastName("Moraes" + i);
		person.setGender("Male" + i);
		return person;
	}

	public Person findById(String id) {
		logger.info("Finding one person!");

		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setAdress("Niterói");
		person.setFirstName("Davyd");
		person.setLastName("Moraes");
		person.setGender("Male");

		return person;
	}
}
