package com.davyd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davyd.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
