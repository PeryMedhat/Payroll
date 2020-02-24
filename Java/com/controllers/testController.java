package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.models.TestModel;
import com.services.EmployeeStructService;

@RestController
@RequestMapping("/api")
public class testController {
	
	@RequestMapping(value = { "/test" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
public TestModel testController (@RequestBody TestModel model) {
		
		
		
		String code = model.getCode();
		String name = model.getName();
		TestModel testMyModel = new TestModel();
		
		testMyModel.setCode(code);
		testMyModel.setName(model.getName());
	return testMyModel;
	
	
	
}

}
