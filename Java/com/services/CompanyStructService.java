package com.services;

import java.text.ParseException;
import java.util.List;
import com.entities.CompanyStructChild;
import com.entities.CompanyStructParent;
import com.entities.CompanyStructSubparent;
import com.models.CompanyStructModel;

public interface CompanyStructService {

	public String processTheIncommingModel(CompanyStructModel employee) throws Exception;

	public CompanyStructModel getTheParent(String code);

	public List<CompanyStructModel> getTheSubParentsOfParent(String code);

	public List<CompanyStructModel> getTheChildrenOfSubParent(String subCode);

	public List<CompanyStructModel> getTheChildrenOfParent(String parentCode);

	public CompanyStructParent getParent(String code);

	public CompanyStructSubparent getSubParent(String code);

	public Boolean isParent(String parentCode);

	public Boolean isSubParent(String parentCode);

	public CompanyStructModel getParentOfSub(CompanyStructSubparent sub);

	public CompanyStructModel getParentOfChild(CompanyStructChild child);

	public List<CompanyStructModel> getTheSubParentsOfSubParent(String parentCode);

	public List<CompanyStructModel> getParentChain(String code);

	public List<CompanyStructModel> getSubParentChain(String code);

	public CompanyStructModel getTheSubParent(String code);

	public List<CompanyStructModel> getChildChain(String code);

	public CompanyStructModel getTheChild(String code);

	public String updateCompanyStructure(CompanyStructModel employee);

	public String deleteParent(String code);
	public String deleteSubParent(String code);
	public String deleteChild(String code);

	public List<CompanyStructSubparent> getSubOfSub(String code);

	public void delmitParent(String code, String endDate) throws ParseException;
	public void delmitSubParent(String code, String endDate) throws ParseException;
	public void delmitChild(String code, String endDate) throws ParseException;

	public String copyCompanyStructure(CompanyStructModel employeeStructModel, String todayDate);

	

}
