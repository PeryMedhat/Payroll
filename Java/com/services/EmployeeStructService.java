package com.services;

import java.util.Map;

import com.entities.EmpStructParent;
import com.entities.EmpStructSubparent;
import com.models.EmployeeStructModel;

public interface EmployeeStructService {

	public String processTheIncommingModel(EmployeeStructModel employee) throws Exception;

	public Map<String, Object> getTheSubParentsOfSubParent(String parentCode);

	public Map<String, Object> getTheParent(String code);

	Map<String, Object> getTheSubParentsOfParent(String code);

	Map<String, Object> getTheChildrenOfSubParent(String subCode);

	Map<String, Object> getTheChildrenOfParent(String parentCode);

	EmpStructParent getParent(String code);

	EmpStructSubparent getSubParent(String code);

}
