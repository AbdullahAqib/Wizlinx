package com.wizlinx.app.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wizlinx.app.io.entity.StudentEntity;

public interface StudentRepository extends PagingAndSortingRepository<StudentEntity, Integer>{
	public Page<StudentEntity> findAllByGender(String gender, Pageable pageable);
	public List<StudentEntity> findAllByGender(String gender);
}
