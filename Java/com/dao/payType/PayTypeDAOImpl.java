package com.dao.payType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.payType.PayType;
@Repository
public class PayTypeDAOImpl implements PayTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addPayType(PayType payType) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();			
		session.saveOrUpdate(payType);
	}

	@Override
	public PayType getPayType() {
		// TODO Auto-generated method stub
		return null;
	}


}
