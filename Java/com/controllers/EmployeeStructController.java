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
				myMap.putAll(getParentChain(code));
			}
			else if(isSub) {
				myMap.putAll(getSubParentChain(code));
			}else {
				//myMap.putAll(getChildChain(code));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return myMap;
	}
	
	//To be removed to service class ***
	public Map<String,Object> getParentChain(String code){
		Map<String,Object> parentMap = new HashMap<String,Object>();
		//getting parent
		Map<String,Object> parent = empService.getTheParent(code);
		
		EmpStructParent parentObject = empService.getParent(code);
		
		Map<String,Object> subOfParent = empService.getTheSubParentsOfParent(parentObject.getCommID().getCode());
		Map<String,Object> childrenOfParent =empService.getTheChildrenOfParent(parentObject.getCommID().getCode());
		List<EmpStructSubparent> subOfParentObject = parentObject.getSubParents();
		
		for(Integer i = 0; i<subOfParentObject.size();i++) {
			Map<String,Object> subOfSub = empService.getTheSubParentsOfSubParent(subOfParentObject.get(i).getCommID().getCode());
			Map<String,Object> childrenOfSubMap = empService.getTheChildrenOfSubParent(subOfParentObject.get(i).getCommID().getCode());
			
			parentMap.putAll(childrenOfSubMap);
			parentMap.putAll(subOfSub);
		}
		parentMap.putAll(parent);
		parentMap.putAll(subOfParent);
		parentMap.putAll(childrenOfParent);
		return parentMap;		
	}
	
	public Map<String,Object> getSubParentChain(String code){
		Map<String,Object> subparentChainMap = new HashMap<String,Object>();
		
		Map<String,Object> subParents = empService.getTheSubParentsOfSubParent(code);
		
		
		subparentChainMap.putAll(subParents);
		return subparentChainMap;
	}
	
	
	
}
		
		
