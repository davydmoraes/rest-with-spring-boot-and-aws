package com.davyd.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davyd.controllers.PersonController;
import com.davyd.data.vo.PersonVO;
import com.davyd.exceptions.ResourceNotFoundException;
import com.davyd.mapper.DozerMapper;
import com.davyd.models.Person;
import com.davyd.repositories.PersonRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

	public PersonVO create(PersonVO person) throws Exception {
		logger.info("Creating a new person!");

		var output = personRepository.save(DozerMapper.parseObject(person, Person.class));

		PersonVO personVO = DozerMapper.parseObject(output, PersonVO.class);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	public PersonVO update(PersonVO personVO) throws Exception {
		logger.info("Updating person!");

		Person person = personRepository.findById(personVO.getKey()).//
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		person.setAddress(personVO.getAddress());
		person.setFirstName(personVO.getFirstName());
		person.setLastName(personVO.getLastName());
		person.setGender(personVO.getGender());

		PersonVO vo = DozerMapper.parseObject(personRepository.save(person), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public List<PersonVO> findAll() {
		logger.info("Finding all the people!");

		List<PersonVO> people = DozerMapper.parseListObject(personRepository.findAll(), PersonVO.class);
		people.stream()//
				.forEach(p -> {
					try {
						p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
		return people;
	}

	public PersonVO findById(Long id) throws Exception {
		logger.info("Finding one person!");

		var person = personRepository.findById(id).//
				orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		PersonVO personVO = DozerMapper.parseObject(person, PersonVO.class);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

}
