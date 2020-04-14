package com.dao.lookups;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.lookups.InputValue;

@Repository
public class InputValueLookupDAOImpl implements InputValueLookupDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<InputValue> getListOfIntervals() {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		Query<InputValue> theQuery = session.createQuery("from InputValue",InputValue.class);
		List<InputValue> inputValues = theQuery.list();
					
		return inputValues;
	}

	@Override
	public InputValue getIntervalByName(String name) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		Query<InputValue> theQuery = session.createQuery("from InputValue where name =:name",InputValue.class);
		theQuery.setParameter("name", name);
		InputValue theInputValue = (InputValue)theQuery.getSingleResult();
				
		return theInputValue;
		}


}
