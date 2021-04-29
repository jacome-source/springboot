package com.jacome.example.thymeleaf.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jacome.example.thymeleaf.model.Student;

/**
 * Sempre abaixo do SpringBootApplication
 * @author diego
 *
 */
@Controller
public class HelloController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/sendData")
	public ModelAndView sendData() {
		// Objeto que redireciona para uma página "data" podendo conter vários parâmetros "message"
		ModelAndView mav = new ModelAndView("data");
		mav.addObject("message", "teste");
		return mav;
	}
	
	@RequestMapping("/student")
	public ModelAndView getStudent() {
		// Objeto que redireciona para uma página "data" podendo conter vários parâmetros "message"
		ModelAndView mav = new ModelAndView("student");
		mav.addObject("student", new Student("Jacome",100));
		return mav;
	}
	
	@RequestMapping("/students")
	public ModelAndView getStudents() {
		// Objeto que redireciona para uma página "data" podendo conter vários parâmetros "message"
		ModelAndView mav = new ModelAndView("studentList");
		Student s1 = new Student("Jacome",100);
		Student s2 = new Student("Dalva",10);
		
		List<Student> students = Arrays.asList(s1,s2);		
		mav.addObject("students",students);
		return mav;
	}
	
	@RequestMapping("/studentForm")
	public ModelAndView displayStudentForm() {
		// Objeto que redireciona para uma página "data" podendo conter vários parâmetros "message"
		ModelAndView mav = new ModelAndView("studentForm");
		mav.addObject("student", new Student());
		return mav;
	}
	
	// Verbo HTTP utilizado setado na página, no form
	// @ModelAttribute Faz mapeamento e deserialização automática dos atributos
	@RequestMapping("/saveStudent")
	public ModelAndView saveSudent(@ModelAttribute Student student) {
		// Objeto que redireciona para uma página "data" podendo conter vários parâmetros "message"
		ModelAndView mav = new ModelAndView("result");
		System.out.println(student.getName());
		System.out.println(student.getScore());
		return mav;
	}
	
}
