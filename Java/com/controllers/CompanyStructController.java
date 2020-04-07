package com.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.models.CompanyStructModel;
import com.services.CompanyStructService;

@RestController
@RequestMapping("/companyStructure")
public class CompanyStructController {
	
	@Autowired
	CompanyStructService companyService;
	
	@RequestMapping(value = { "/addCompanyStructure" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String[] addCompanyStructure(@RequestBody List<CompanyStructModel> companyModel) throws Exception {
		String[] flag= new String[companyModel.size()];
		
		for(Integer i =0;i<companyModel.size();i++) {
			flag[i] = companyService.processTheIncommingModel(companyModel.get(i));
		}
		return flag;
	}
	
	@RequestMapping(value = { "/showCompanyStructure" }, method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object>  showCompanyStructure(@RequestParam("code") String code) {
		Map<String,Object> myMap = new HashMap<String,Object>();
		List<CompanyStructModel> myList = new ArrayList<CompanyStructModel>();
		
		Boolean isParent = companyService.isParent(code);
		Boolean isSub = companyService.isSubParent(code);
		
		try {
			if(isParent) {
				myList.addAll(companyService.getParentChain(code));
			}
			else if(isSub) {
				myList.addAll(companyService.getSubParentChain(code));
			}else {
				myList.addAll(companyService.getChildChain(code));
			}
			myMap.put("theChain",myList);
		}catch(Exception e) {
			myMap.put("theChain",null);
		}
		return myMap;
	}

	@RequestMapping(value = { "/getCompanyStructureElement" }, method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object>  getCompanyStructureElement(@RequestParam("code") String code) {
		Map<String,Object> myMap = new HashMap<String,Object>();
		CompanyStructModel model= new CompanyStructModel(); 
		Boolean isParent = companyService.isParent(code);
		Boolean isSub = companyService.isSubParent(code);
		try {
			if(isParent) {
				model =companyService.getTheParent(code);
			}else if(isSub) {
				model =companyService.getTheSubParent(code);
			}else{
				model =companyService.getTheChild(code);}
			myMap.put("theModel",model);
		}catch(Exception e) {
			myMap.put("theChain",null);
		}
		return myMap;
	}

	@RequestMapping(value = { "/updateCompanyStructure" }, method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String updateCompanyStructure(@RequestBody CompanyStructModel companyModel) {
		String flag ="false";
		try {
			flag= companyService.updateCompanyStructure(companyModel);
		}catch(Exception e) {e.printStackTrace();flag ="false";}
		return flag;
	}

	@RequestMapping(value = { "/deleteCompanyStructure" }, method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String deleteCompanyStructure(@RequestParam("code") String code) {
		String isDeleted = "false";
		Boolean isParent = companyService.isParent(code);
		Boolean isSub = companyService.isSubParent(code);
		try {
			if(isParent) {
				isDeleted = companyService.deleteParent(code);
			}else if(isSub) {
				isDeleted = companyService.deleteSubParent(code);				
			}else {
				isDeleted = companyService.deleteChild(code);
			}
		}catch(Exception e) {
			e.printStackTrace();
			isDeleted = "Cannot delete non saved code!";		
		}
		return isDeleted;
	}
	
	@RequestMapping(value = { "/delemitCompanyStructure" }, method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String delemitCompanyStructure(@RequestParam("code") String code,@RequestParam("endDate") String endDate) {
		String isDelemited = "false";
		Boolean isParent = companyService.isParent(code);
		Boolean isSub = companyService.isSubParent(code);
		try {
			if(isParent) {
				companyService.delmitParent(code,endDate);
			}else if(isSub) {
				companyService.delmitSubParent(code,endDate);			
			}else {
				companyService.delmitChild(code,endDate);}
			isDelemited = "true";
		}catch(Exception e) {
			e.printStackTrace();
			isDelemited = "false";}
		return isDelemited;
	}

	@RequestMapping(value = { "/copyCompanyStructure" }, method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String[] copyCompanyStructure(@RequestBody List<CompanyStructModel> companyModel) {
		String[] flag = new String[companyModel.size()];
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String todayDate=dtf.format(now);
		try {
			for(int i=0;i<companyModel.size();i++) {
				flag[i]= companyService.copyCompanyStructure(companyModel.get(i),todayDate);
			}
		}catch(Exception e) {e.printStackTrace();}
		return flag;
	}
}
		
		
