package com.dao;

import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addSubParentToParent(EmpStructSubparent subParent, String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addSubParentToSubParent(EmpStructSubparent subParent, String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addChildToParent(EmpStructChild child, String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addChildToSubParent(EmpStructChild child, String parentCode) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isSubParent(String parentCode) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
