package com.dao.lookups;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.lookups.PaymentMethodLookup;


@Repository
public class PaymentMethodsLookupDAOImpl implements PaymentMethodsLookupDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<PaymentMethodLookup> getListOfPaymentMethodLookup() {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		Query<PaymentMethodLookup> theQuery = session.createQuery("from PaymentMethodLookup",PaymentMethodLookup.class);
		List<PaymentMethodLookup> PaymentMethodLookup = theQuery.list();
			
		return PaymentMethodLookup;
	}

	@Override
	public PaymentMethodLookup getPaymentMethodByName(String name) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		Query<PaymentMethodLookup> theQuery = session.createQuery("from PaymentMethodLookup where name =:name",PaymentMethodLookup.class);
		theQuery.setParameter("name", name);
		PaymentMethodLookup PaymentMethodLookup = (PaymentMethodLookup)theQuery.getSingleResult();
		
		return PaymentMethodLookup;
	}
		
	@Override
	public PaymentMethodLookup getPaymentMethodByCode(String code) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		Query<PaymentMethodLookup> theQuery = session.createQuery("from PaymentMethodLookup where code =:code",PaymentMethodLookup.class);
		theQuery.setParameter("code", code);
		PaymentMethodLookup PaymentMethodLookup = (PaymentMethodLookup)theQuery.getSingleResult();
		
		return PaymentMethodLookup;
	}


}
