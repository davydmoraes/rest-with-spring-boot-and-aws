package com.davyd.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davyd.data.vo.PersonVO;
import com.davyd.exceptions.ResourceNotFoundException;
import com.davyd.mapper.DozerMapper;
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

	public PersonVO create(PersonVO person) {
		logger.info("Creating a new person!");

		var output = personRepository.save(DozerMapper.parseObject(person, Person.class));

		return DozerMapper.parseObject(output, PersonVO.class);
	}

	public PersonVO update(PersonVO personVO) {
		logger.info("Updating person!");

		Person person = personRepository.findById(personVO.getId()).//
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		person.setAddress(personVO.getAddress());
		person.setFirstName(personVO.getFirstName());
		person.setLastName(personVO.getLastName());
		person.setGender(personVO.getGender());

		return DozerMapper.parseObject(personRepository.save(person), PersonVO.class);
	}

	public List<PersonVO> findAll() {
		logger.info("Finding all the people!");

		return DozerMapper.parseListObject(personRepository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person!");

		Person person = personRepository.findById(id).//
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		return DozerMapper.parseObject(person, PersonVO.class);
	}

}
