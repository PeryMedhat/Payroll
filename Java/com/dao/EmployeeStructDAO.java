package com.dao;

import java.util.List;

import com.entities.EmpStructChild;
import com.entities.EmpStructParent;
import com.entities.EmpStructSubparent;

public interface EmployeeStructDAO {
	
	//add parent at the parent table
	public Boolean addParent(EmpStructParent parent);
	
	//get the parent/subParent and use addSubParent method then save it to DB
	public Boolean addSubParentToParent(EmpStructSubparent subParent , String parentCode);
	public Boolean addSubParentToSubParent(EmpStructSubparent subParent , String parentCode);
	
	//get the parent/subParent and use addChild method then save it to DB
	public Boolean addChildToParent(EmpStructChild child,String parentCode );
	public Boolean addChildToSubParent(EmpStructChild child, String parentCode);
	
	//get an Employee by the unique code from specific table
	public EmpStructParent getParent(String code);
	public EmpStructSubparent getSubParent(String code);
	public EmpStructChild getChild(String code);
	
	//Select all from subParent table that has parentCode of that parentCode
	public List<EmpStructSubparent> getSubParentsOfSubParents(String parentCode);
	
	//get the parent then use the getSubParents method 
	public List<EmpStructSubparent> getSubParentsOfParent(String parentCode);
	
	//get the parent then use the getChildren method 
	public List<EmpStructChild> getChildrenOfParent(String parentCode);
	
	//get the subParent then use the getChildren method
	public List<EmpStructChild> getChildrenOfSubParent(String parentCode);
	
	//try to find out if this employee is a parent/subParent
	public Boolean isParent(String parentCode);
	public Boolean isSubParent(String parentCode);

	Boolean addSubParentToSubParent(EmpStructSubparent subParent);
	
	
	
	
}