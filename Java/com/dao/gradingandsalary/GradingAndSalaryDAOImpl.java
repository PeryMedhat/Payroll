package com.dao.gradingandsalary;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.gradingandsalary.GradingAndSalary;

@Repository
public class GradingAndSalaryDAOImpl implements GradingAndSalaryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addGradingAndSalary(GradingAndSalary GradingAndSalary) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();			
		session.saveOrUpdate(GradingAndSalary);
	}

	@Override
	public GradingAndSalary getGradingAndSalary(String code) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		GradingAndSalary GradingAndSalary ;
		Query<GradingAndSalary> theQuery = session.createQuery("From GradingAndSalary where id =:id)", GradingAndSalary.class); 
		theQuery.setParameter("id",code);
		
		GradingAndSalary = theQuery.getSingleResult();
		
		return GradingAndSalary;		
	}

	
	@Override
	public GradingAndSalary getGradingAndSalaryById(int id) {
		Session session = sessionFactory.getCurrentSession();
		GradingAndSalary GradingAndSalary = session.get(GradingAndSalary.class, id);
		return GradingAndSalary;		
	}
	
	@Override
	public void deleteGradingAndSalary(String code) {
		Session session = sessionFactory.getCurrentSession();
		GradingAndSalary GradingAndSalary=getGradingAndSalary(code);
		session.delete(GradingAndSalary);
	}
	
	
	
	@Override
	public List<GradingAndSalary> getAllGradingAndSalarys(){
		// get the session
		Session session = sessionFactory.getCurrentSession();
		List<GradingAndSalary> GradingAndSalarys = new ArrayList<GradingAndSalary>();
		Query<GradingAndSalary> theQuery =session.createQuery("From GradingAndSalary",GradingAndSalary.class);
		
		GradingAndSalarys = theQuery.getResultList();
		return GradingAndSalarys;		
	}
	
	
	
	
	
	
	
	
	
	

}
