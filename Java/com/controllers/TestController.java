package com.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.models.TestModel;

@RestController
@RequestMapping("/api")
public class TestController {
	
	@RequestMapping(value = { "/test" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TestModel testController(@RequestBody TestModel model) {
		String code = model.getCode();
		String name = model.getName();
		TestModel testMyModel = new TestModel();
		
		testMyModel.setCode(code);
		testMyModel.setName(name);
		
		return testMyModel;
	
	}

}
