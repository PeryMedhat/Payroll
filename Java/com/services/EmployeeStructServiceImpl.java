package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.EmployeeStructDAO;
import com.entities.CommonID;
import com.entities.EmpStructChild;
import com.entities.EmpStructParent;
import com.entities.EmpStructSubparent;
import com.models.EmployeeModel;


@Service
public class EmployeeStructServiceImpl implements EmployeeStructService {
	
	@Autowired
	private EmployeeStructDAO employeeDAO;
	
	@Override
	@Transactional
	public Boolean processTheIncommingModel(EmployeeModel employee) {
		
		//commonID data
		CommonID commId = new CommonID(employee.getStartDate(),
										employee.getEndDate(),
										employee.getCode(),
										employee.getName());
		
		//check if this model has parent and type of his parent
		Boolean hisParentIsParent = employeeDAO.isParent(employee.getParentCode());
		Boolean hisParentIsSubParent = employeeDAO.isSubParent(employee.getParentCode());
		
		//flag to save boolean value to check if the data successfully added to our database or not
		Boolean missionIsDone = false;
		
		//Process the model to know weather it is a parent/SubParent/Child
		if(!employee.getHasParent() && employee.getHasChild()) {
			
			//the model has no parent but has a child ==> save as parent
			EmpStructParent parent = new EmpStructParent(commId);
			missionIsDone = employeeDAO.addParent(parent);
		
		}else if(employee.getHasParent() && employee.getHasChild()
				 && hisParentIsParent) {
			
			//the model has parent also has child and his parent is parent ==> save as subParent 
			EmpStructSubparent subParent = new EmpStructSubparent(0,null,commId);
			missionIsDone =employeeDAO.addSubParentToParent(subParent, employee.getParentCode());
			
		}else if( employee.getHasParent() && employee.getHasChild()
				 && hisParentIsSubParent) {
			
			//the model has parent also has child and his parent is subParent ==> save as subParent 
			EmpStructSubparent subParent = new EmpStructSubparent(1,employee.getParentCode(),commId);
			missionIsDone =employeeDAO.addSubParentToSubParent(subParent, employee.getParentCode());
		
		}else if(employee.getHasParent() && !employee.getHasChild()
				 && hisParentIsParent) {
			
			//the model has parent and has no child and his parent is parent ==>save as child to parent
			EmpStructChild child = new EmpStructChild(commId);
			missionIsDone =employeeDAO.addChildToParent(child, employee.getParentCode());
			
		}else if(employee.getHasParent() && !employee.getHasChild()
				 && hisParentIsSubParent) {
			
			//the model has parent and has no child and his parent is subParent ==>save as child to subParent
			EmpStructChild child = new EmpStructChild(commId);
			missionIsDone =employeeDAO.addChildToSubParent(child,  employee.getParentCode());
			
		}else {
			
			//has no Parent and no children
			EmpStructParent parent = new EmpStructParent(commId);
			missionIsDone =employeeDAO.addParent(parent);
		}
		return missionIsDone;
		
		
	}

}
