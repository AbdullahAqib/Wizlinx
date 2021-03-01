package com.wizlinx.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.wizlinx.app.io.entity.StudentEntity;
import com.wizlinx.app.service.ReportService;
import com.wizlinx.app.service.StudentService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("students")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	ReportService reportService;

	@GetMapping
	public List<StudentEntity> getStudents() {
		return studentService.getAllStudents();
	}
	
	@PostMapping
	public StudentEntity addStudent(@RequestBody StudentEntity std) {
		return studentService.addStudent(std);
	}
	
	@GetMapping("/{id}")
	public Optional<StudentEntity> addStudent(@PathVariable int id) {
		return studentService.getStudent(id);
	}
	
	
	@GetMapping(path="/report/{gender}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String generateReport(@PathVariable String gender,
								 @RequestParam(value="page", defaultValue = "0") int page,
								 @RequestParam (value="limit", defaultValue = "10") int limit) throws FileNotFoundException, JRException {
		
		return reportService.exportStudentReport(gender, page, limit);
	
	}
	
//	@GetMapping(path="/exportreport/{format}/{gender}")
//	@ResponseBody
//	public StreamingResponseBody generateReportandExport(@PathVariable String format,
//										@PathVariable String gender,
//								 @RequestParam(value="page", defaultValue = "0") int page,
//								 @RequestParam (value="limit", defaultValue = "10") int limit) throws FileNotFoundException, JRException {
//		
//		String path = reportService.exportStudentReport(gender, page, limit, format);
//		
//		InputStream inputStream = new FileInputStream(new File(path));
//
//        return outputStream -> {
//            int nRead;
//            byte[] data = new byte[1024];
//            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
//                outputStream.write(data, 0, nRead);
//            }
//            inputStream.close();
//        };
//	
//        
//        
//	}
	
	 @RequestMapping(value = "/exportreport/{format}/{gender}", method = RequestMethod.GET)
	 @ResponseBody
	    public StreamingResponseBody exportReport(HttpServletResponse response,
	    		@PathVariable String format,
				@PathVariable String gender,
		 @RequestParam(value="page", defaultValue = "0") int page,
		 @RequestParam (value="limit", defaultValue = "10") int limit) throws IOException, JRException {

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=\"webpage.pdf\"");
	        
	        String path = reportService.exportStudentReport(gender, page, limit, format);
	        
	        InputStream inputStream = new FileInputStream(new File(path));

	        return outputStream -> {
	            int nRead;
	            byte[] data = new byte[1024];
	            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	                outputStream.write(data, 0, nRead);
	            }
	            inputStream.close();
	        };
	    }
	
	 @RequestMapping(value = "/download", method = RequestMethod.GET)
	 @ResponseBody
	    public StreamingResponseBody getSteamingFile(HttpServletResponse response) throws IOException, JRException {

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=\"webpage.pdf\"");
	        
	        String path = reportService.exportStudentReport("female", 0, 10, "pdf");
	        
	        InputStream inputStream = new FileInputStream(new File(path));

	        return outputStream -> {
	            int nRead;
	            byte[] data = new byte[1024];
	            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	                outputStream.write(data, 0, nRead);
	            }
	            inputStream.close();
	        };
	    }
}
