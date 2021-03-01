package com.wizlinx.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wizlinx.app.io.entity.StudentEntity;
import com.wizlinx.app.io.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentReposity;

	public List<StudentEntity> getAllStudents() {
		return (List<StudentEntity>) studentReposity.findAll();
	}

	public StudentEntity addStudent(StudentEntity std) {
		return studentReposity.save(std);
	}

	public Optional<StudentEntity> getStudent(int id) {
		return studentReposity.findById(id);
	}

	public List<StudentEntity> getStudentsByGenderPageable(String gender, int page, int limit) {

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<StudentEntity> studentsPage = studentReposity.findAllByGender(gender, pageableRequest);

		List<StudentEntity> students = studentsPage.getContent();
		
		return students;

	}
	
	public List<StudentEntity> getStudentsByGender(String gender) {

		List<StudentEntity> students = studentReposity.findAllByGender(gender);
		
		return students;

	}
	
	public int getStudentCountByGender(String gender) {
		
		return studentReposity.findAllByGender(gender).size();
		
	}
	
}
