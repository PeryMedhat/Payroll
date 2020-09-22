package com.dummy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.models.financialstructure.BanksModel;
import com.models.financialstructure.CostCenterStructureModel;
import com.models.financialstructure.GLAccountsModel;
import com.models.financialstructure.PaymentMehodModel;

@RestController
@RequestMapping("/dummyFinancialStructure")
public class DummyClassFinancialStruct {
	
	@RequestMapping(value = {
			"/getAllBanks" }, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getAllBanks() {
		Map<String, Object> myMap = new HashMap<String, Object>();
		List<BanksModel> myList = new ArrayList<BanksModel>();
		try {
			for(int i=0;i<10;i++) {
				BanksModel bank = new BanksModel();
				bank.setCode("code"+i);
				bank.setBranch("branch no :"+i);
				bank.setCountry("Egypt");
				bank.setName("Bank");
				
				myList.add(bank);
			}
			myMap.put("theChainOfBanks", myList);

		} catch (Exception e) {
			myMap.put("theChainOfBanks", null);
			e.printStackTrace();
		}

		return myMap;
	}
	
	
	@RequestMapping(value = {
		"/getAllCostCenters" }, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getAllCostCenters() {
	Map<String, Object> myMap = new HashMap<String, Object>();
	List<CostCenterStructureModel> myList = new ArrayList<CostCenterStructureModel>();
	try {
		for(int i=0;i<10;i++) {
			CostCenterStructureModel costCenter = new CostCenterStructureModel();
			costCenter.setCode("code"+i);
			costCenter.setName("costCenter"+i);
			
			myList.add(costCenter);
		}
		myMap.put("theChainOfcostCenters", myList);
	
	} catch (Exception e) {
		myMap.put("theChainOfcostCenters", null);
		e.printStackTrace();
	}
	
	return myMap;
	}
	
	
	
	
	@RequestMapping(value = {
		"/getAllGLAccounts" }, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getAllGLAccounts() {
	Map<String, Object> myMap = new HashMap<String, Object>();
	List<GLAccountsModel> myList = new ArrayList<GLAccountsModel>();
	try {
		for(int i=0;i<10;i++) {
			GLAccountsModel gLAccount = new GLAccountsModel();
			gLAccount.setCode("code"+i);
			gLAccount.setName("gLAccount"+i);
			gLAccount.setType("type");
						
			myList.add(gLAccount);
		}
		myMap.put("theChainOfgLAccounts", myList);
	
	} catch (Exception e) {
		myMap.put("theChainOfgLAccounts", null);
		e.printStackTrace();
	}
	
	return myMap;
	}
	
	
	
	@RequestMapping(value = {
		"/getAllPaymentMethods" }, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> getAllPaymentMethods() {
	Map<String, Object> myMap = new HashMap<String, Object>();
	List<PaymentMehodModel> myList = new ArrayList<PaymentMehodModel>();
	
	try {
		for(int i=0;i<10;i++) {
			PaymentMehodModel paymentMehod = new PaymentMehodModel();
			paymentMehod.setStartDate("03/09/2020");
			paymentMehod.setEndDate("13/09/2020");
			paymentMehod.setPaymentMethod("cash");
						
			myList.add(paymentMehod);
		}
		myMap.put("theChainOfpaymentMethods", myList);
	
	} catch (Exception e) {
		myMap.put("theChainOfpaymentMethods", null);
		e.printStackTrace();
	}
	
	return myMap;
	}
}
