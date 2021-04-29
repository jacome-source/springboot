package com.jacome.example.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jacome.example.springdatajpa.entities.Student;
import com.jacome.example.springdatajpa.repos.StudentRepository;

@SpringBootTest
class SpringdatajpaApplicationTests {

	@Autowired
	private StudentRepository repo;
	
	@Test
	void testSaveStudent() {
		Student std = new Student();
		std.setId(1L);
		std.setName("Jácome");
		std.setTestScore(100);
		repo.save(std);
		
		Student saved = repo.findById(1L).get();
		assertNotNull(saved);
	}

	
	@Test
	void testUpdateStudent() {
		Student std = repo.findById(1L).get();
		std.setTestScore(10);
		repo.save(std);
		Student saved = repo.findById(1L).get();
		assertEquals(10,saved.getTestScore());
	}
	
	
	@Test
	void testDeleteStudent() {
		Student std = repo.findById(1L).get();
		repo.delete(std);
		boolean empty = repo.findById(1L).isEmpty();
		assertTrue(empty);
	}
}
