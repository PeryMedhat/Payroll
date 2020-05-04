package com.dao.payType;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
	public PayType getPayType(String code) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		PayType payType ;
		Query<PayType> theQuery = session.createQuery("From PayType where commID=(select id from CommonID where code =:code)", PayType.class); 
		theQuery.setParameter("code",code);
		
		payType = theQuery.getSingleResult();
		
		return payType;		
	}

	
	@Override
	public PayType getPayTypeById(int id) {
		Session session = sessionFactory.getCurrentSession();
		PayType payType = session.get(PayType.class, id);
		return payType;		
	}
	
	@Override
	public void deletePayType(String code) {
		Session session = sessionFactory.getCurrentSession();
		PayType payType=getPayType(code);
		session.delete(payType);
	}
	
	
	
	@Override
	public List<PayType> getAllPayTypes(){
		// get the session
		Session session = sessionFactory.getCurrentSession();
		List<PayType> payTypes = new ArrayList<PayType>();
		Query<PayType> theQuery =session.createQuery("From PayType",PayType.class);
		
		payTypes = theQuery.getResultList();
		return payTypes;		
	}
	
	
	
	
	
	
	
	
	
	

}
