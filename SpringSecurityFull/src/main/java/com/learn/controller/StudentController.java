package com.learn.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.model.Student;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
	
	
	private static final List<Student> STUDENTS = Arrays.asList(
		      new Student(1, "aman"),
		      new Student(2, "ajay"),
		      new Student(3, "anna")
		    );
	
	
	   @GetMapping(path = "{studentId}")
	    public Student getStudent(@PathVariable("studentId") Integer studentId) {
	        return STUDENTS.stream().filter(student->student.getStudentId().equals(studentId)).
	        		findFirst().orElseThrow(()->new IllegalStateException( "Student " + studentId + " does not exists"));
	    }

}
