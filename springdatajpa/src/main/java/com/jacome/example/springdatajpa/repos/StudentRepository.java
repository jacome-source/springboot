package com.jacome.example.springdatajpa.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacome.example.springdatajpa.entities.Student;

/**
 * JpaRepository<T, R>
 * T: Tipo da classe
 * R: Tipo do ID da classe
 * 
 * @author diego
 *
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

}
