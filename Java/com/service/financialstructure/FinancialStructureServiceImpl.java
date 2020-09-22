package com.service.financialstructure;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.financialstructure.FinancialStructDAO;
import com.entities.banks.Banks;
import com.entities.costCenterStructure.CostCenterStructure;
import com.entities.glaccounts.GLAccounts;
import com.entities.paymentMethods.PaymentMehod;
import com.models.financialstructure.BanksModel;
import com.models.financialstructure.CostCenterStructureModel;
import com.models.financialstructure.GLAccountsModel;
import com.models.financialstructure.PaymentMehodModel;
import com.rest.errorhandling.UniqunessException;

@Service
public class FinancialStructureServiceImpl implements FinancialStructure{
	@Autowired
	private FinancialStructDAO financialStructDAO;
	
	@Override
	@Transactional
	public void addGLAccount(GLAccountsModel glAccount) {
		GLAccounts object= new GLAccounts();
		
		object.setCode(glAccount.getCode());
		object.setName(glAccount.getName());
		object.setType(glAccount.getType());
		
		try {
			financialStructDAO.addGLAccount(object);
		}catch(Exception e) {throw new UniqunessException("Gl Account "+" is already saved!");}
	
	}


	@Override
	@Transactional
	public List<GLAccountsModel> getAllGLAccounts() {
		List<GLAccountsModel> glAccountsModel = new ArrayList<GLAccountsModel>();
		List<GLAccounts> glAccountsObject=financialStructDAO.getAllGLAccounts();
		
		for(int i=0; i<glAccountsObject.size();i++) {
			GLAccountsModel model = new GLAccountsModel();
			model.setCode(glAccountsObject.get(i).getCode());
			model.setName(glAccountsObject.get(i).getName());
			model.setType(glAccountsObject.get(i).getType());
			
			glAccountsModel.add(model);
		}
		
		return glAccountsModel;
	}


	@Override
	@Transactional
	public void addCostCenter(CostCenterStructureModel costCenter) {
		CostCenterStructure object= new CostCenterStructure();
		object.setCode(costCenter.getCode());
		object.setName(costCenter.getName());
		
		try {
			financialStructDAO.addCostCenter(object);
		}catch(Exception e) {throw new UniqunessException("Cost Center "+" is already saved!");}
	}





	@Override
	@Transactional
	public List<CostCenterStructureModel> getAllCostCenters() {
		List<CostCenterStructureModel> costCenterStructureModel = new ArrayList<CostCenterStructureModel>();
		List<CostCenterStructure> costCenterStructureObject=financialStructDAO.getAllCostCenters();
		
		for(int i=0; i<costCenterStructureObject.size();i++) {
			CostCenterStructureModel model = new CostCenterStructureModel();
			model.setCode(costCenterStructureObject.get(i).getCode());
			model.setName(costCenterStructureObject.get(i).getName());
			
			costCenterStructureModel.add(model);
		}
		return costCenterStructureModel;
	}


	@Override
	@Transactional
	public void addBank(BanksModel bank) {
		
		Banks object= new Banks();
		object.setBranch(bank.getBranch());
		object.setCode(bank.getCode());
		object.setCountry(bank.getCountry());
		object.setName(bank.getName());
		
		try {
			financialStructDAO.addBank(object);
		}catch(Exception e) {throw new UniqunessException("Bank "+" is already saved!");}
	}


	@Override
	@Transactional
	public List<BanksModel> getAllBanks() {
		List<BanksModel> banksModel = new ArrayList<BanksModel>();
		List<Banks> banksObject=financialStructDAO.getAllBanks();
		
		for(int i=0; i<banksObject.size();i++) {
			BanksModel model = new BanksModel();
			model.setBranch(banksObject.get(i).getBranch());
			model.setCode(banksObject.get(i).getCode());
			model.setCountry(banksObject.get(i).getCountry());
			model.setName(banksObject.get(i).getName());
			
			banksModel.add(model);
		}
		
		return banksModel;
	}


	@Override
	@Transactional
	public void addPaymentMethod(PaymentMehodModel paymentMethod) throws ParseException {
		Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(paymentMethod.getStartDate());
		Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(paymentMethod.getEndDate());
		
		PaymentMehod object = new PaymentMehod();
		object.setStartDate(startDate);
		object.setEndDate(endDate);
		object.setPaymentMethod(paymentMethod.getPaymentMethod());
		
		
		try {
			financialStructDAO.addPaymentMethod(object);
		}catch(Exception e) {throw new UniqunessException("payment method"+" is already saved!");}
	
		
	}


	@Override
	@Transactional
	public List<PaymentMehodModel> getAllPaymentMethods() {
		List<PaymentMehodModel> paymentMehodModel = new ArrayList<PaymentMehodModel>();
		List<PaymentMehod> PaymentMehodObject=financialStructDAO.getAllPaymentMethods();
		
		for(int i=0; i<PaymentMehodObject.size();i++) {
			DateFormat dateFormat = new SimpleDateFormat();
			PaymentMehodModel model = new PaymentMehodModel();
			
			String startDate = dateFormat.format(PaymentMehodObject.get(i).getStartDate());
			String endDate = dateFormat.format(PaymentMehodObject.get(i).getEndDate());
			
			model.setStartDate(startDate);
			model.setEndDate(endDate);
			model.setPaymentMethod(PaymentMehodObject.get(i).getPaymentMethod());
			
			paymentMehodModel.add(model);
		}
		
		return paymentMehodModel;
	}
	
	
}
