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

import com.models.EmployeeStructModel;
import com.services.EmployeeStructService;

@RestController
@RequestMapping("/employeeStructure")
public class EmployeeStructController {
	
	@Autowired
	EmployeeStructService empService;
	
	@RequestMapping(value = { "/addEmployeeStructure" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String[] addEmployeeStructure(@RequestBody List<EmployeeStructModel> employeeModel) {
		String[] flag= new String[employeeModel.size()];
		try {
			for(Integer i =0;i<employeeModel.size();i++) {
				flag[i] = empService.processTheIncommingModel(employeeModel.get(i));
			}
		} catch (Exception e) {
		} 
		return flag;
	}
	
	@RequestMapping(value = { "/showEmployeeStructure" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object>  showEmployeeStructure(@RequestBody EmployeeStructModel employeeModel) {
		Map<String,Object> myMap = new HashMap<String,Object>();
		String code =employeeModel.getCode();
		
		Boolean isParent = empService.isParent(code);
		Boolean isSub = empService.isSubParent(code);
		
		try {
			if(isParent) {
				myMap.putAll(empService.getParentChain(code));
			}
			else if(isSub) {
				myMap.putAll(empService.getSubParentChain(code));
			}else {
				//myMap.putAll(getChildChain(code));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return myMap;
	}
	
	
	
}
		
		
