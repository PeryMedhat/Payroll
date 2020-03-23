package com.dao;

import java.util.ArrayList;
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
			e.printStackTrace();
			session.clear(); 
			return false;
		}
	}
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
			return false;
		}
	}

	@Override
	public EmpStructParent getParent(String code) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		EmpStructParent parent;
		try {
			// get all subParents with that parent code
			Query<EmpStructParent> theQuery = session.createQuery("from EmpStructParent where commID=(select id from CommonID where code =:code)", EmpStructParent.class);
			theQuery.setParameter("code",code);
			
			parent = (EmpStructParent) theQuery.getSingleResult();
				
		}catch(Exception e) {return null;}
					
		return parent;
	}
	public EmpStructSubparent getSubParent(String code) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		EmpStructSubparent subParent ;
		try {
			// get all subParents with that parent code
			Query<EmpStructSubparent> theQuery = session.createQuery("from EmpStructSubparent where commID=(select id from CommonID where code =:code)", EmpStructSubparent.class);
			theQuery.setParameter("code",code);
					
			subParent = (EmpStructSubparent) theQuery.getSingleResult();
		}catch(Exception e) {return null;}
								
		return subParent;
	}
	public EmpStructChild getChild(String code) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		EmpStructChild child;
		try {
			// get all subParents with that parent code
			Query<EmpStructChild> theQuery = session.createQuery("from EmpStructChild where commID=(select id from CommonID where code =:code)", EmpStructChild.class);
			theQuery.setParameter("code",code);
			
			child = (EmpStructChild) theQuery.getSingleResult();
					
		}catch(Exception e) {return null;}						
		return child;
	}

	@Override
	public EmpStructParent getParentById(int id) {
		Session session = sessionFactory.getCurrentSession();
		EmpStructParent parent;
		try {
			parent = session.get(EmpStructParent.class,id);
		}catch(Exception e) {return null;}
		
		return parent;
	}
	@Override
	public EmpStructChild getChildById(int id) {
		Session session = sessionFactory.getCurrentSession();
		EmpStructChild child;
		try {
			child = session.get(EmpStructChild.class, id);
		}catch(Exception e) {return null;}
		return child;	
	}
	@Override
	public EmpStructSubparent getSubById(int id) {
		Session session = sessionFactory.getCurrentSession();
		EmpStructSubparent sub;
		try {
			sub = session.get(EmpStructSubparent.class, id);
		}catch(Exception e) {return null;}
		return sub;
	}
		
	@Override
	public List<EmpStructSubparent> getSubParentsOfSubParents(String parentCode) {
		List<EmpStructSubparent> empStructSubParents = new ArrayList<EmpStructSubparent>();
		// get the session
		Session session = sessionFactory.getCurrentSession();
		try {
			// get all subParents with that parent code
			Query<EmpStructSubparent> theQuery = session.createQuery("from EmpStructSubparent s where s.parentCode =:parentCode", EmpStructSubparent.class);
			theQuery.setParameter("parentCode", parentCode);
			
			empStructSubParents = theQuery.getResultList();
					
		}catch(Exception e) {return null;}
		
		return empStructSubParents;
	}
	public List<EmpStructSubparent> getSubParentsOfParent(String parentCode) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		
		// get all subParents with that parent code
		Query<EmpStructParent> theQuery = session.createQuery("from EmpStructParent where commID=(select id from CommonID where code =:code)", EmpStructParent.class);
		theQuery.setParameter("code",parentCode);
				
		EmpStructParent parent = (EmpStructParent) theQuery.getSingleResult();
								
		// get all subParents with that parent code
		Query<EmpStructSubparent> query = session.createQuery("from EmpStructSubparent s where s.parent =:parentId", EmpStructSubparent.class);
		theQuery.setParameter("parentId", parent.getId());
				
		List<EmpStructSubparent> empStructSubParents = query.getResultList();
						
		return empStructSubParents;
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
			return false;
		}
		return isParent;
	}
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
			return false;
		}
		return isSubParent;
	}

	@Override
	public String deleteParent(String code) {
		Session session = sessionFactory.getCurrentSession();
		Boolean isDeleted =false;
		try {
			EmpStructParent parent=getParent(code);
			session.delete(parent);
			isDeleted=true;
		}catch(Exception e) {
			isDeleted =false;
			return isDeleted.toString();
		}
		return isDeleted.toString();
	}

	@Override
	public String deleteSubParent(String code) {
		Session session = sessionFactory.getCurrentSession();
		Boolean isDeleted =false;
		try {
			EmpStructSubparent sub = getSubParent(code);
			session.delete(sub);
			isDeleted=true;
		}catch(Exception e) {
			isDeleted =false;
			return isDeleted.toString();
		}
		return isDeleted.toString();
	}

	@Override
	public String deleteChild(String code) {
		Session session = sessionFactory.getCurrentSession();
		Boolean isDeleted =false;
		try {
			EmpStructChild child = getChild(code);
			session.delete(child);
			isDeleted=true;
		}catch(Exception e) {
			isDeleted =false;
			return isDeleted.toString();
		}
		return isDeleted.toString();
	}
	
	
	
}
