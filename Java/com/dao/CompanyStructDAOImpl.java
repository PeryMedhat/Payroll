package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.CompanyStructChild;
import com.entities.CompanyStructParent;
import com.entities.CompanyStructSubparent;

@Repository
public class CompanyStructDAOImpl implements CompanyStructDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Boolean addParent(CompanyStructParent parent) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		//then save the parent
		session.saveOrUpdate(parent);
		return true;
			
	}
	@Override
	public Boolean addSubParentToParent(CompanyStructSubparent subParent, String parentCode) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
				
		//get the parent using parent code
		Query<CompanyStructParent> query = session.createQuery("from CompanyStructParent where commID=(select id from CompanyCommonID where code =:code)", CompanyStructParent.class);
		query.setParameter("code", parentCode);
		CompanyStructParent parent = (CompanyStructParent) query.getSingleResult();
			
		//Add the subParent to this parent 
		parent.addSubParent(subParent);
		session.saveOrUpdate(subParent);
		return true;
		
		}
	public Boolean addSubParentToSubParent(CompanyStructSubparent subParent) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(subParent);
		return true;
		
		
	}

	@Override
	public Boolean addChildToParent(CompanyStructChild child, String parentCode) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
		
		//get the parent using parent code
		Query<CompanyStructParent> query = session.createQuery("from CompanyStructParent where commID=(select id from CompanyCommonID where code =:code)", CompanyStructParent.class);
		query.setParameter("code", parentCode);
		CompanyStructParent parent = (CompanyStructParent) query.getSingleResult();
			
		//Add the subParent to this parent 
		parent.addChild(child);
		session.saveOrUpdate(child);
		return true;
	
	}
	public Boolean addChildToSubParent(CompanyStructChild child, String parentCode) {
		// get the session 
		Session session = sessionFactory.getCurrentSession();
								
		
		//get the parent using subParet code
		Query<CompanyStructSubparent> query = session.createQuery("from CompanyStructSubparent where commID=(select id from CompanyCommonID where code =:code)", CompanyStructSubparent.class);
		query.setParameter("code", parentCode);
		CompanyStructSubparent subParent = (CompanyStructSubparent) query.getSingleResult();
					
		//Add the subParent to this parent 
		subParent.addChild(child);
		session.saveOrUpdate(child);
		return true;
	}

	@Override
	public CompanyStructParent getParent(String code) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		CompanyStructParent parent;
		try {
			// get all subParents with that parent code
			Query<CompanyStructParent> theQuery = session.createQuery("from CompanyStructParent where commID=(select id from CompanyCommonID where code =:code)", CompanyStructParent.class);
			theQuery.setParameter("code",code);
			
			parent = (CompanyStructParent) theQuery.getSingleResult();
				
		}catch(Exception e) {return null;}
					
		return parent;
	}
	public CompanyStructSubparent getSubParent(String code) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		CompanyStructSubparent subParent ;
		try {
			// get all subParents with that parent code
			Query<CompanyStructSubparent> theQuery = session.createQuery("from CompanyStructSubparent where commID=(select id from CompanyCommonID where code =:code)", CompanyStructSubparent.class);
			theQuery.setParameter("code",code);
					
			subParent = (CompanyStructSubparent) theQuery.getSingleResult();
		}catch(Exception e) {return null;}
								
		return subParent;
	}
	public CompanyStructChild getChild(String code) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		CompanyStructChild child;
		try {
			// get all subParents with that parent code
			Query<CompanyStructChild> theQuery = session.createQuery("from CompanyStructChild where commID=(select id from CompanyCommonID where code =:code)", CompanyStructChild.class);
			theQuery.setParameter("code",code);
			
			child = (CompanyStructChild) theQuery.getSingleResult();
					
		}catch(Exception e) {return null;}						
		return child;
	}

	@Override
	public CompanyStructParent getParentById(int id) {
		Session session = sessionFactory.getCurrentSession();
		CompanyStructParent parent;
		try {
			parent = session.get(CompanyStructParent.class,id);
		}catch(Exception e) {return null;}
		
		return parent;
	}
	@Override
	public CompanyStructChild getChildById(int id) {
		Session session = sessionFactory.getCurrentSession();
		CompanyStructChild child;
		try {
			child = session.get(CompanyStructChild.class, id);
		}catch(Exception e) {return null;}
		return child;	
	}
	@Override
	public CompanyStructSubparent getSubById(int id) {
		Session session = sessionFactory.getCurrentSession();
		CompanyStructSubparent sub;
		try {
			sub = session.get(CompanyStructSubparent.class, id);
		}catch(Exception e) {return null;}
		return sub;
	}
		
	@Override
	public List<CompanyStructSubparent> getSubParentsOfSubParents(String parentCode) {
		List<CompanyStructSubparent> companyStructSubParents = new ArrayList<CompanyStructSubparent>();
		// get the session
		Session session = sessionFactory.getCurrentSession();
		try {
			// get all subParents with that parent code
			Query<CompanyStructSubparent> theQuery = session.createQuery("from CompanyStructSubparent s where s.parentCode =:parentCode", CompanyStructSubparent.class);
			theQuery.setParameter("parentCode", parentCode);
			
			companyStructSubParents = theQuery.getResultList();
					
		}catch(Exception e) {return null;}
		
		return companyStructSubParents;
	}
	public List<CompanyStructSubparent> getSubParentsOfParent(String parentCode) {
		// get the session
		Session session = sessionFactory.getCurrentSession();
		
		// get all subParents with that parent code
		Query<CompanyStructParent> theQuery = session.createQuery("from CompanyStructParent where commID=(select id from CompanyCommonID where code =:code)", CompanyStructParent.class);
		theQuery.setParameter("code",parentCode);
				
		CompanyStructParent parent = (CompanyStructParent) theQuery.getSingleResult();
								
		// get all subParents with that parent code
		Query<CompanyStructSubparent> query = session.createQuery("from CompanyStructSubparent s where s.parent =:parentId", CompanyStructSubparent.class);
		theQuery.setParameter("parentId", parent.getId());
				
		List<CompanyStructSubparent> companyStructSubParents = query.getResultList();
						
		return companyStructSubParents;
	}

	@Override
	public Boolean isParent(String parentCode) {
		Boolean isParent;
		
		// get the session 
		Session session = sessionFactory.getCurrentSession();
								
		try {
			//get the parent using parent code
			Query<CompanyStructParent> query = session.createQuery("from CompanyStructParent where commID=(select id from CompanyCommonID where code =:code)", CompanyStructParent.class);
			query.setParameter("code", parentCode);
			CompanyStructParent parent = (CompanyStructParent) query.getSingleResult();
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
			Query<CompanyStructSubparent> query = session.createQuery("from CompanyStructSubparent where commID=(select id from CompanyCommonID where code =:code)", CompanyStructSubparent.class);
			query.setParameter("code", parentCode);
			CompanyStructSubparent subParent = (CompanyStructSubparent) query.getSingleResult();
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
			CompanyStructParent parent=getParent(code);
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
			CompanyStructSubparent sub = getSubParent(code);
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
			CompanyStructChild child = getChild(code);
			session.delete(child);
			isDeleted=true;
		}catch(Exception e) {
			isDeleted =false;
			return isDeleted.toString();
		}
		return isDeleted.toString();
	}
	
	
	
}
