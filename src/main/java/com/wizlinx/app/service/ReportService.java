package com.wizlinx.app.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.wizlinx.app.io.entity.StudentEntity;
import com.wizlinx.app.io.repository.StudentRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {

	@Autowired
	private StudentService studentService;

	public String exportStudentReport(String gender, int page, int limit) throws FileNotFoundException, JRException {
		String path = "C:\\Users\\atif.jamil\\Desktop\\Reports";

		List<StudentEntity> students = studentService.getStudentsByGenderPageable(gender, page, limit);

		// load file and compile it
		File file = ResourceUtils.getFile("classpath:student_report.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "Abdullah Aqib");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\students.html");

		File myObj = new File(path + "\\students.html");
		Scanner myReader = new Scanner(myObj);
		StringBuilder builder = new StringBuilder();
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			builder.append(data);
		}
		myReader.close();

		String temp = builder.toString().replace("\"", "$");
		
		int count = studentService.getStudentCountByGender(gender);

		return "{ \"data\":" + "\"" + temp + "\", \"count\": \""+ count + "\"}";
	}

//	public String exportStudentReport(String gender) throws FileNotFoundException, JRException {
//		String path = "C:\\Users\\atif.jamil\\Desktop\\Reports";
//
//		List<StudentEntity> students = studentService.getStudentsByGender(gender);
//
//		// load file and compile it
//		File file = ResourceUtils.getFile("classpath:student_report.jrxml");
//		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
//		Map<String, Object> parameters = new HashMap<>();
//		parameters.put("createdBy", "Abdullah Aqib");
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//		JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\students.html");
//
//		File myObj = new File(path + "\\students.html");
//		Scanner myReader = new Scanner(myObj);
//		StringBuilder builder = new StringBuilder();
//		while (myReader.hasNextLine()) {
//			String data = myReader.nextLine();
//			builder.append(data);
//		}
//		myReader.close();
//
////        String temp = builder.toString().replace("\"", "$");
//
//		return "{ \"data\":" + "\"" + builder.toString() + "\"}";
//	}

	public String exportStudentReport(String gender, int page, int limit, String format)
			throws FileNotFoundException, JRException {

		String path = "C:\\Users\\atif.jamil\\Desktop\\Reports";

		List<StudentEntity> students = studentService.getStudentsByGenderPageable(gender, page, limit);

		// load file and compile it
		File file = ResourceUtils.getFile("classpath:student_report.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "Abdullah Aqib");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\students.pdf");

		return path + "\\students.pdf";

	}
}