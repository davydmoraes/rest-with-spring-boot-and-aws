package com.davyd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davyd.data.vo.PersonVO;
import com.davyd.services.PersonService;
import com.davyd.utils.MediaType;

@RestController
@RequestMapping(value = "/person/v1")
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping(value = "/{id}", //
			produces = { MediaType.JSON, MediaType.XML, MediaType.YML })
	public PersonVO findById(@PathVariable(value = "id") Long id) throws Exception {

		return personService.findById(id);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {

		personService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@PostMapping(produces = { MediaType.JSON, MediaType.XML, MediaType.YML }, //
			consumes = { MediaType.JSON, MediaType.XML, MediaType.YML })
	public PersonVO create(@RequestBody PersonVO person) throws Exception {
		return personService.create(person);
	}

	@PutMapping(produces = { MediaType.JSON, MediaType.XML, MediaType.YML }, //
			consumes = { MediaType.JSON, MediaType.XML, MediaType.YML })
	public PersonVO update(@RequestBody PersonVO person) throws Exception {
		return personService.update(person);
	}

	@GetMapping(produces = { MediaType.JSON, MediaType.XML, MediaType.YML })
	public List<PersonVO> findAll() throws Exception {
		return personService.findAll();
	}

}
