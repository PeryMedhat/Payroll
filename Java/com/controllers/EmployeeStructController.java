package com.controllers;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entities.CommonID;
import com.entities.EmpStructParent;
import com.models.EmployeeModel;
import com.models.TestModel;
import com.services.EmployeeStructService;


@Controller
@RequestMapping("/employeeStructure")
public class EmployeeStructController {
	
	@Autowired
	EmployeeStructService empService;
		
	@GetMapping("/showFormToAddEmployeeStructureModel")
	public String addEmployeeForm(Model theModel) {
		EmployeeModel emp = new EmployeeModel();
		theModel.addAttribute("employeeStructure", emp);
		return "addEmployeeStructure";
	}
	
	@InitBinder     
	public void initBinder(WebDataBinder binder){
	     binder.registerCustomEditor(Date.class,     
	     (PropertyEditor) new CustomDateEditor(
	      new SimpleDateFormat("yyyy/mm/dd"), true, 10));   
	}
	


	}
		
		
