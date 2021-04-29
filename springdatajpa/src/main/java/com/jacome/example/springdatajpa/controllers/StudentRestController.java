package com.jacome.example.springdatajpa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jacome.example.springdatajpa.entities.Student;
import com.jacome.example.springdatajpa.repos.StudentRepository;

@RestController
public class StudentRestController {

	@Autowired
	private StudentRepository studentRepository;
	
	@RequestMapping(value="/students/", method=RequestMethod.GET)
	public List<Student> getStudents(){
		return studentRepository.findAll();
	}
	
	@GetMapping(value="/students/{id}")
	public Student getStudent(@PathVariable("id") long id) {
		return studentRepository.findById(id).get();
	}
	
	@RequestMapping(value="/students/", method=RequestMethod.POST)
	public Student createStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	@RequestMapping(value="/students/", method=RequestMethod.PUT)
	public Student updateStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	@RequestMapping(value="/students/{id}", method=RequestMethod.DELETE)
	public void deleteStudent(@PathVariable("id") long id){
		studentRepository.deleteById(id);
	}
	
}
