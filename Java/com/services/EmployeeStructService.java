package com.services;

import java.util.Map;

import com.entities.EmpStructChild;
import com.entities.EmpStructParent;
import com.entities.EmpStructSubparent;
import com.models.EmployeeStructModel;

public interface EmployeeStructService {

	public String processTheIncommingModel(EmployeeStructModel employee) throws Exception;

	public Map<String, Object> getTheSubParentsOfSubParent(String parentCode);

	public Map<String, Object> getTheParent(String code);

	public Map<String, Object> getTheSubParentsOfParent(String code);

	public Map<String, Object> getTheChildrenOfSubParent(String subCode);

	public Map<String, Object> getTheChildrenOfParent(String parentCode);

	public EmpStructParent getParent(String code);

	public EmpStructSubparent getSubParent(String code);

	public Boolean isParent(String parentCode);

	public Boolean isSubParent(String parentCode);

	public Map<String, Object> getParentOfSub(EmpStructSubparent sub);

	public Map<String, Object> getParentOfChild(EmpStructChild child);

}
