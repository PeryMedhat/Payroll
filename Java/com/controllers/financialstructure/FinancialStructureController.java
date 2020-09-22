package com.controllers.financialstructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.models.financialstructure.BanksModel;
import com.models.financialstructure.CostCenterStructureModel;
import com.models.financialstructure.GLAccountsModel;
import com.models.financialstructure.PaymentMehodModel;
import com.service.financialstructure.FinancialStructure;

@RestController
@RequestMapping("/financialStructure")
public class FinancialStructureController {

	@Autowired
	FinancialStructure financialStructure;

	@RequestMapping(value = { "/addBanks" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void addBanks(@RequestBody List<BanksModel> banks) throws Exception {
		for (Integer i = 0; i < banks.size(); i++) {
			financialStructure.addBank(banks.get(i));
		}
	}

	@RequestMapping(value = { "/addCostCenters" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void addCostCenters(@RequestBody List<CostCenterStructureModel> costCenters) throws Exception {
		for (Integer i = 0; i < costCenters.size(); i++) {
			financialStructure.addCostCenter(costCenters.get(i));
		}
	}

	@RequestMapping(value = { "/addGLAccounts" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void addGLAccounts(@RequestBody List<GLAccountsModel> gLAccounts) throws Exception {
		for (Integer i = 0; i < gLAccounts.size(); i++) {
			financialStructure.addGLAccount(gLAccounts.get(i));
		}
	}

	@RequestMapping(value = { "/addPaymentMethods" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void addPaymentMethods(@RequestBody List<PaymentMehodModel> PaymentMehods) throws Exception {
		for (Integer i = 0; i < PaymentMehods.size(); i++) {
			financialStructure.addPaymentMethod(PaymentMehods.get(i));
		}
	}

}
