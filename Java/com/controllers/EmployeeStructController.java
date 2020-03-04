package com.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.EmpStructParent;
import com.entities.EmpStructSubparent;
import com.models.EmployeeStructModel;
import com.services.EmployeeStructService;

@RestController
@RequestMapping("/employeeStructure")
public class EmployeeStructController {
	
	@Autowired
	EmployeeStructService empService;
		
	@RequestMapping(value = { "/addEmployeeStructure" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addEmployeeStructure(@RequestBody EmployeeStructModel employeeModel) {
		String flag = "false";
		try {
			flag = empService.processTheIncommingModel(employeeModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@RequestMapping(value = { "/showEmployeeStructure" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object>  showEmployeeStructure(@RequestBody EmployeeStructModel employeeModel) {
		Map<String,Object> myMap = new HashMap<String,Object>();
		
		//getting parent
		Map<String,Object> parent = empService.getTheParent(employeeModel.getCode());
		
		EmpStructParent parentObject = empService.getParent(employeeModel.getCode());
		
		Map<String,Object> subOfParent = empService.getTheSubParentsOfParent(parentObject.getCommID().getCode());
		Map<String,Object> childrenOfParent =empService.getTheChildrenOfParent(parentObject.getCommID().getCode());
		
		List<EmpStructSubparent> subOfParentObject = parentObject.getSubParents();
		for(Integer i = 0; i<subOfParentObject.size();i++) {
			Map<String,Object> subOfSub = empService.getTheSubParentsOfSubParent(subOfParentObject.get(i).getCommID().getCode());
			Map<String,Object> childrenOfSubMap = empService.getTheChildrenOfSubParent(subOfParentObject.get(i).getCommID().getCode());
			
			myMap.putAll(childrenOfSubMap);
			myMap.putAll(subOfSub);
		}
		myMap.putAll(parent);
		myMap.putAll(subOfParent);
		myMap.putAll(childrenOfParent);
		
		return myMap;
	
	}
	
	
}
		
		
