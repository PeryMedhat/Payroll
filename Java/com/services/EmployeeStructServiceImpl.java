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
		
		CommonID commId = new CommonID(employee.getStartDate(),
										employee.getEndDate(),
										employee.getCode(),
										employee.getName());
								
		Boolean hisParentIsParent = employeeDAO.isParent(employee.getParentCode());
		Boolean hisParentIsSubParent = employeeDAO.isSubParent(employee.getParentCode());
		Boolean missionIsDone = false;
		//Process the model to know weather it is a parent/SubParent/Child
		if(!employee.getHasParent() && employee.getHasChild()) {
			EmpStructParent parent = new EmpStructParent(commId);
			missionIsDone = employeeDAO.addParent(parent);
		
		}else if(employee.getHasParent() && employee.getHasChild()
				 && hisParentIsParent) {
			EmpStructSubparent subParent = new EmpStructSubparent(0,null,commId);
			missionIsDone =employeeDAO.addSubParentToParent(subParent, employee.getParentCode());
			
		}else if( employee.getHasParent() && employee.getHasChild()
				 && hisParentIsSubParent) {
			EmpStructSubparent subParent = new EmpStructSubparent(1,employee.getParentCode(),commId);
			missionIsDone =employeeDAO.addSubParentToSubParent(subParent, employee.getParentCode());
		
		}else if(employee.getHasParent() && !employee.getHasChild()
				 && hisParentIsParent) {
			EmpStructChild child = new EmpStructChild(commId);
			missionIsDone =employeeDAO.addChildToParent(child, employee.getParentCode());
			
		}else if(employee.getHasParent() && !employee.getHasChild()
				 && hisParentIsSubParent) {
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
