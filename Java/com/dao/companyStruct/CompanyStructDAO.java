package com.dao.companyStruct;

import java.util.List;

import com.entities.companyStruct.CompanyStructChild;
import com.entities.companyStruct.CompanyStructParent;
import com.entities.companyStruct.CompanyStructSubparent;

public interface CompanyStructDAO {
	
	//add parent at the parent table
	public Boolean addParent(CompanyStructParent parent);
	
	//get the parent/subParent and use addSubParent method then save it to DB
	public Boolean addSubParentToParent(CompanyStructSubparent subParent , String parentCode);
	public Boolean addSubParentToSubParent(CompanyStructSubparent subParent);
	
	//get the parent/subParent and use addChild method then save it to DB
	public Boolean addChildToParent(CompanyStructChild child,String parentCode );
	public Boolean addChildToSubParent(CompanyStructChild child, String parentCode);
	
	//get an Company by the unique code from specific table
	public CompanyStructParent getParent(String code);
	public CompanyStructSubparent getSubParent(String code);
	public CompanyStructChild getChild(String code);
	
	//Select all from subParent table that has parentCode of that parentCode
	public List<CompanyStructSubparent> getSubParentsOfSubParents(String parentCode);
	
	//get the parent then use the getSubParents method 
	public List<CompanyStructSubparent> getSubParentsOfParent(String parentCode);
	
	//try to find out if this Company is a parent/subParent
	public Boolean isParent(String parentCode);
	public Boolean isSubParent(String parentCode);

	public CompanyStructParent getParentById(int id);
	public CompanyStructChild getChildById(int id);
	public CompanyStructSubparent getSubById(int id);

	public String deleteParent(String code);
	public String deleteSubParent(String code);
	public String deleteChild(String code);
	

}
