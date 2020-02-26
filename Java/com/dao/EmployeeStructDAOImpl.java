package com.dao;

import java.util.List;

import org.hibernate.query.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.EmpStructChild;
import com.entities.EmpStructParent;
import com.entities.EmpStructSubparent;

@Repository
public class EmployeeStructDAOImpl implements EmployeeStructDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Boolean addParent(EmpStructParent parent) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		
		try {			
			//then save the parent
			session.saveOrUpdate(parent);
			return true;
			
		}catch(Exception e) {
			//clear session and return false
			session.clear(); 
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public Boolean addSubParentToParent(EmpStructSubparent subParent, String parentCode) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
				
		try {
			//get the parent using parent code
			Query<EmpStructParent> query = session.createQuery("from EmpStructParent where commID=(select id from CommonID where code =:code)", EmpStructParent.class);
			query.setParameter("code", parentCode);
			EmpStructParent parent = (EmpStructParent) query.getSingleResult();
			
			//Add the subParent to this parent 
			parent.addSubParent(subParent);
			session.saveOrUpdate(subParent);
			return true;
		}catch(Exception e) {
			//clear session and return false
			session.clear(); 
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean addSubParentToSubParent(EmpStructSubparent subParent) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
						
		try {
			session.saveOrUpdate(subParent);
			return true;
							
		}catch(Exception e) {
			//clear session and return false
			session.clear(); 
			return false;
		}
	}

	@Override
	public Boolean addChildToParent(EmpStructChild child, String parentCode) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
						
		try {
			//get the parent using parent code
			Query<EmpStructParent> query = session.createQuery("from EmpStructParent where commID=(select id from CommonID where code =:code)", EmpStructParent.class);
			query.setParameter("code", parentCode);
			EmpStructParent parent = (EmpStructParent) query.getSingleResult();
			
			//Add the subParent to this parent 
			parent.addChild(child);
			session.saveOrUpdate(child);
			return true;
		}catch(Exception e) {
			//clear session and return false
			session.clear(); 
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean addChildToSubParent(EmpStructChild child, String parentCode) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
								
		try {
			//get the parent using subParet code
			Query<EmpStructSubparent> query = session.createQuery("from EmpStructSubparent where commID=(select id from CommonID where code =:code)", EmpStructSubparent.class);
			query.setParameter("code", parentCode);
			EmpStructSubparent subParent = (EmpStructSubparent) query.getSingleResult();
					
			//Add the subParent to this parent 
			subParent.addChild(child);
			session.saveOrUpdate(child);
			return true;
		}catch(Exception e) {
			//clear session and return false
			session.clear(); 
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public EmpStructParent getParent(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmpStructSubparent getSubParent(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmpStructChild getChild(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpStructSubparent> getSubParentsOfSubParents(String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpStructSubparent> getSubParentsOfParent(String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpStructChild> getChildrenOfParent(String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpStructChild> getChildrenOfSubParent(String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isParent(String parentCode) {
		Boolean isParent;
		
		// get the session 
		Session session = sessionFactory.getCurrentSession();
								
		try {
			//get the parent using parent code
			Query<EmpStructParent> query = session.createQuery("from EmpStructParent where commID=(select id from CommonID where code =:code)", EmpStructParent.class);
			query.setParameter("code", parentCode);
			EmpStructParent parent = (EmpStructParent) query.getSingleResult();
			if(parent!=null) {
				isParent= true;
			}else{
				isParent= false;
			}
		}catch(Exception e) {
			//clear session and return false
			session.clear(); 
			e.printStackTrace();
			return false;
		}
		return isParent;
	}

	@Override
	public Boolean isSubParent(String parentCode) {
		Boolean isSubParent;
		
		// get the session 
		Session session = sessionFactory.getCurrentSession();
								
		try {
			//get the parent using parent code
			Query<EmpStructSubparent> query = session.createQuery("from EmpStructSubparent where commID=(select id from CommonID where code =:code)", EmpStructSubparent.class);
			query.setParameter("code", parentCode);
			EmpStructSubparent subParent = (EmpStructSubparent) query.getSingleResult();
			if(subParent!=null) {
				isSubParent= true;
			}else{
				isSubParent= false;
			}
		}catch(Exception e) {
			//clear session and return false
			session.clear(); 
			e.printStackTrace();
			return false;
		}
		return isSubParent;
	}

	

}
