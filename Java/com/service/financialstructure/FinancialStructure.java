package com.service.financialstructure;

import java.text.ParseException;
import java.util.List;

import com.models.financialstructure.BanksModel;
import com.models.financialstructure.CostCenterStructureModel;
import com.models.financialstructure.GLAccountsModel;
import com.models.financialstructure.PaymentMehodModel;

public interface FinancialStructure {
	
	public void addGLAccount(GLAccountsModel glAccount);
	public List<GLAccountsModel> getAllGLAccounts();
	
	public void addCostCenter(CostCenterStructureModel costCenter);
	public List<CostCenterStructureModel> getAllCostCenters();
	
	public void addBank(BanksModel bank);
	public List<BanksModel> getAllBanks();
	
	public void addPaymentMethod(PaymentMehodModel paymentMethod) throws ParseException;
	public List<PaymentMehodModel> getAllPaymentMethods();
}
