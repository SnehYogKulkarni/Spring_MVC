package com.gl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.entities.*;
import com.gl.service.*;

@Controller
@RequestMapping("/student")
public class StudentContoller {
	// add the student from the service
	@Autowired
	private StudentService studentService;
	
	public StudentContoller(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	

	@RequestMapping("/list")
	public String listStudent(Model model) {
		// get all the student from the service
		List<Student> student = studentService.findAll();
		System.out.print("list"+student.toString());
		model.addAttribute("students", student);
		// send over to our form
		return "student-list";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		// get the student from the service
		Student student = new Student();
		// set student as a model attribute to pre-populate the form
		theModel.addAttribute("students", student);
		System.out.print("list for add"+student.toString());
		// send over to our form
		return "student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId, Model theModel) {
		// get the student from the service
		Student student = studentService.findById(theId);
		// set student as a model attribute to pre-populate the form
		theModel.addAttribute("students", student);
		System.out.println("Show for update"+student);
		// send over to our form
		return "student-form";
	}

	@PostMapping("/save")
	public String saveStudent(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("country") String country) {

		Student student;
		if (id != 0) {
			student = studentService.findById(id);
			student.setName(name);
			student.setDepartment(department);
			student.setCountry(country);
		} else
			student = new Student(name, department, country);
		// save the Book
		studentService.save(student);

		// use a redirect to prevent duplicate submissions
		return "redirect:/student/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("studentId") int theId) {

		// delete the student
		studentService.deleteById(theId);

		// redirect to /student/list
		return "redirect:/student/list";

	}

}
