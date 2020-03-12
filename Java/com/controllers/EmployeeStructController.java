package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
			e.printStackTrace();
		} 
		return flag;
	}
	
	@RequestMapping(value = { "/showEmployeeStructure" }, method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object>  showEmployeeStructure(@RequestParam("code") String code) {
		Map<String,Object> myMap = new HashMap<String,Object>();
		List<EmployeeStructModel> myList = new ArrayList<EmployeeStructModel>();
		
		Boolean isParent = empService.isParent(code);
		Boolean isSub = empService.isSubParent(code);
		
		try {
			if(isParent) {
				myList.addAll(empService.getParentChain(code));
			}
			else if(isSub) {
				myList.addAll(empService.getSubParentChain(code));
			}else {
				myList.addAll(empService.getChildChain(code));
			}
			myMap.put("theChain",myList);
		}catch(Exception e) {
			myMap.put("Code is not as an employee group code",null);
		}
		return myMap;
	}

	@RequestMapping(value = { "/updateEmployeeStructure" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String updateEmployeeStructure(@RequestBody EmployeeStructModel employeeModel,@RequestParam("id") int id ) {
		return empService.updateEmployeeStructure(employeeModel,id);
	}


}
		
		
