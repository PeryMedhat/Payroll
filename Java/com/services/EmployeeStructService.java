package com.services;

import java.util.List;
import com.entities.EmpStructChild;
import com.entities.EmpStructParent;
import com.entities.EmpStructSubparent;
import com.models.EmployeeStructModel;

public interface EmployeeStructService {

	public String processTheIncommingModel(EmployeeStructModel employee) throws Exception;

	public EmployeeStructModel getTheParent(String code);

	public List<EmployeeStructModel> getTheSubParentsOfParent(String code);

	public List<EmployeeStructModel> getTheChildrenOfSubParent(String subCode);

	public List<EmployeeStructModel> getTheChildrenOfParent(String parentCode);

	public EmpStructParent getParent(String code);

	public EmpStructSubparent getSubParent(String code);

	public Boolean isParent(String parentCode);

	public Boolean isSubParent(String parentCode);

	public EmployeeStructModel getParentOfSub(EmpStructSubparent sub);

	public EmployeeStructModel getParentOfChild(EmpStructChild child);

	public List<EmployeeStructModel> getTheSubParentsOfSubParent(String parentCode);

	public List<EmployeeStructModel> getParentChain(String code);

	public List<EmployeeStructModel> getSubParentChain(String code);

	public EmployeeStructModel getTheSubParent(String code);

	public List<EmployeeStructModel> getChildChain(String code);

	public EmployeeStructModel getTheChild(String code);

	public String updateEmployeeStructure(EmployeeStructModel employee,int id);


}
