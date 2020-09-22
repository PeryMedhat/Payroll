package com.dao.financialstructure;

import java.util.List;

import com.entities.banks.Banks;
import com.entities.costCenterStructure.CostCenterStructure;
import com.entities.glaccounts.GLAccounts;
import com.entities.paymentMethods.PaymentMehod;


public interface FinancialStructDAO {

	public void addGLAccount(GLAccounts glAccount);
	public List<GLAccounts> getAllGLAccounts();
	
	public void addCostCenter(CostCenterStructure costCenter);
	public List<CostCenterStructure> getAllCostCenters();
	
	public void addBank(Banks bank);
	public List<Banks> getAllBanks();
	
	public void addPaymentMethod(PaymentMehod paymentMethod);
	public List<PaymentMehod> getAllPaymentMethods();
	
}
