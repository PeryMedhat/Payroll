package com.dao.financialstructure;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.banks.Banks;
import com.entities.costCenterStructure.CostCenterStructure;
import com.entities.glaccounts.GLAccounts;
import com.entities.paymentMethods.PaymentMehod;

@Repository
public class FinancialStructDAOImpl implements FinancialStructDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addGLAccount(GLAccounts glAccount) {
		Session session = sessionFactory.getCurrentSession();			
		session.saveOrUpdate(glAccount);
	}

	@Override
	public List<GLAccounts> getAllGLAccounts() {
		Session session = sessionFactory.getCurrentSession();
		List<GLAccounts> GLAccounts ;
		Query<GLAccounts> theQuery = session.createQuery("From GLAccounts ", GLAccounts.class); 
		
		GLAccounts = theQuery.getResultList();
		return GLAccounts;
	}

	@Override
	public void addCostCenter(CostCenterStructure costCenter) {
		Session session = sessionFactory.getCurrentSession();			
		session.saveOrUpdate(costCenter);
	}

	@Override
	public List<CostCenterStructure> getAllCostCenters() {
		Session session = sessionFactory.getCurrentSession();
		List<CostCenterStructure> CostCenterStructure ;
		Query<CostCenterStructure> theQuery = session.createQuery("From CostCenterStructure ", CostCenterStructure.class); 
		
		CostCenterStructure = theQuery.getResultList();
		return CostCenterStructure;
	}

	@Override
	public void addBank(Banks bank) {
		Session session = sessionFactory.getCurrentSession();			
		session.saveOrUpdate(bank);
	}

	@Override
	public List<Banks> getAllBanks() {
		Session session = sessionFactory.getCurrentSession();
		List<Banks> Banks ;
		Query<Banks> theQuery = session.createQuery("From Banks ", Banks.class); 
		
		Banks = theQuery.getResultList();
		return Banks;
	}

	@Override
	public void addPaymentMethod(PaymentMehod paymentMethod) {
		Session session = sessionFactory.getCurrentSession();			
		session.saveOrUpdate(paymentMethod);
		
	}

	@Override
	public List<PaymentMehod> getAllPaymentMethods() {
		Session session = sessionFactory.getCurrentSession();
		List<PaymentMehod> PaymentMehod ;
		Query<PaymentMehod> theQuery = session.createQuery("From PaymentMehod ", PaymentMehod.class); 
		
		PaymentMehod = theQuery.getResultList();
		return PaymentMehod;
	}
	

}
