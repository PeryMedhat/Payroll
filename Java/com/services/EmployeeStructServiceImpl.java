package com.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.EmployeeStructDAO;
import com.entities.CommonID;
import com.entities.EmpStructChild;
import com.entities.EmpStructParent;
import com.entities.EmpStructSubparent;
import com.models.EmployeeStructModel;


@Service
public class EmployeeStructServiceImpl implements EmployeeStructService {
	
	@Autowired
	private EmployeeStructDAO employeeDAO;
	
	@Override
	@Transactional
	public String processTheIncommingModel(EmployeeStructModel employee) throws Exception {

		if (employee.getName()!= null && 
				employee.getCode()!=null&&
				employee.getEndDate()!=null&&
				employee.getStartDate()!=null&&
				employee.getHasParent()!=null&&
				employee.getHasChild()!=null){
			
			//conversion of dates 
			Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(employee.getStartDate());  
			Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(employee.getEndDate());
			
			//commonID data
			CommonID commId = new CommonID(startDate,
										endDate,
									employee.getCode(),
								employee.getName());
			
			String parentCode =employee.getParentCode();
			
			//check if this model has parent and type of his parent
			Boolean hisParentIsParent = employeeDAO.isParent(parentCode);
			Boolean hisParentIsSubParent = employeeDAO.isSubParent(parentCode);
			
			//flag to save boolean value to check if the data successfully added to our database or not
			Boolean savedEmployeeStruct = false;

			//Process the model to know if it is a parent/SubParent/Child
			if(!employee.getHasParent() && employee.getHasChild()) {
				
				//the model has no parent but has a child ==> save as parent
				EmpStructParent parent = new EmpStructParent(commId);
				savedEmployeeStruct = employeeDAO.addParent(parent);
			
			}else if(employee.getHasParent() && employee.getHasChild()
					 && hisParentIsParent) {
				
				//the model has parent also has child and his parent is parent ==> save as subParent 
				EmpStructSubparent subParent = new EmpStructSubparent(0,null,commId);
				savedEmployeeStruct =employeeDAO.addSubParentToParent(subParent, parentCode);
				
			}else if( employee.getHasParent() && employee.getHasChild()
					 && hisParentIsSubParent) {
				
				//the model has parent also has child and his parent is subParent ==> save as subParent 
				EmpStructSubparent subParent = new EmpStructSubparent(1,parentCode,commId);
				savedEmployeeStruct =employeeDAO.addSubParentToSubParent(subParent);
			
			}else if(employee.getHasParent() && !employee.getHasChild()
					 && hisParentIsParent) {
				
				//the model has parent and has no child and his parent is parent ==>save as child to parent
				EmpStructChild child = new EmpStructChild(commId);
				savedEmployeeStruct =employeeDAO.addChildToParent(child, parentCode);
				
			}else if(employee.getHasParent() && !employee.getHasChild()
					 && hisParentIsSubParent) {
				
				//the model has parent and has no child and his parent is subParent ==>save as child to subParent
				EmpStructChild child = new EmpStructChild(commId);
				savedEmployeeStruct =employeeDAO.addChildToSubParent(child,parentCode);
				
			}else {
				
				//has no Parent and no children
				EmpStructParent parent = new EmpStructParent(commId);
				savedEmployeeStruct =employeeDAO.addParent(parent);
			}
			return savedEmployeeStruct.toString();
			
			
		}
		else {
			return "values is missing for saving the employee struct";
		}
		
	}
	
	@Override
	@Transactional
	public Map<String,Object> getTheSubParentsOfSubParent(String parentCode){
		List<EmpStructSubparent> subParents =employeeDAO.getSubParentsOfSubParents(parentCode);
		Map<String,Object> subParentsModel =new HashMap<String,Object>();
		for(Integer i = 0;i<subParents.size();i++) {
			
			//create a model
			EmployeeStructModel model = new EmployeeStructModel();
			DateFormat dateFormat = new SimpleDateFormat();
			boolean hasParent = (subParents.get(i).getHasParent()==0)?false:true;
			String startDate = dateFormat.format(subParents.get(i).getCommID().getStartDate());
			String endDate = dateFormat.format(subParents.get(i).getCommID().getEndDate());
			model.setStartDate(startDate);
			model.setEndDate(endDate);
			model.setHasParent(hasParent);
			model.setCode(subParents.get(i).getCommID().getCode());
			model.setName(subParents.get(i).getCommID().getName());
			model.setParentCode(subParents.get(i).getParentCode());
				
			subParentsModel.put("sub-parent of sub-parent number:"+(i).toString(),model);
		
		}
		
		return subParentsModel;
	
	}
	
	@Override
	@Transactional
	public Map<String,Object> getTheParent(String code){
		EmpStructParent parent = employeeDAO.getParent(code);
		
		EmployeeStructModel model = new EmployeeStructModel();
		DateFormat dateFormat = new SimpleDateFormat();
				
		String startDate = dateFormat.format(parent.getCommID().getStartDate());
		String endDate = dateFormat.format(parent.getCommID().getEndDate());
		
		model.setStartDate(startDate);
		model.setEndDate(endDate);
		model.setCode(parent.getCommID().getCode());
		model.setName(parent.getCommID().getName());
		Map<String,Object> parentModel =new HashMap<String,Object>();
		parentModel.put("the parent",model);
		return parentModel;
		}

	@Override
	@Transactional
	public Map<String,Object> getTheSubParentsOfParent(String code){
		EmpStructParent parent =employeeDAO.getParent(code);
		
		List<EmpStructSubparent> subParentsOfParent = parent.getSubParents();
		Map<String,Object> subParentsModel =new HashMap<String,Object>();
		
		for(Integer i=0;i<subParentsOfParent.size();i++) {
			
		
			EmployeeStructModel model = new EmployeeStructModel();
			DateFormat dateFormat = new SimpleDateFormat();
			boolean hasParent = (subParentsOfParent.get(i).getHasParent()==0)?false:true;
			
			String startDate = dateFormat.format(subParentsOfParent.get(i).getCommID().getStartDate());
			String endDate = dateFormat.format(subParentsOfParent.get(i).getCommID().getEndDate());
			
			model.setStartDate(startDate);
			model.setEndDate(endDate);
			model.setHasParent(hasParent);
			model.setCode(subParentsOfParent.get(i).getCommID().getCode());
			model.setName(subParentsOfParent.get(i).getCommID().getName());
			model.setParentCode(subParentsOfParent.get(i).getParentCode());

			subParentsModel.put("sub-parent of parent number:"+(i).toString(),model);
		}
		
		return subParentsModel;
	}
		
	@Override
	@Transactional
	public Map<String,Object> getTheChildrenOfSubParent(String subCode){
		EmpStructSubparent subParent = employeeDAO.getSubParent(subCode);
		List<EmpStructChild> childrenOfSub = subParent.getChildren();
		Map<String,Object> childrenModel =new HashMap<String,Object>();
		
		for(Integer i=0;i<childrenOfSub.size();i++) {
			EmployeeStructModel model = new EmployeeStructModel();
			DateFormat dateFormat = new SimpleDateFormat();
			
			String startDate = dateFormat.format(childrenOfSub.get(i).getCommID().getStartDate());
			String endDate = dateFormat.format(childrenOfSub.get(i).getCommID().getEndDate());
			
			model.setStartDate(startDate);
			model.setEndDate(endDate);
			model.setCode(childrenOfSub.get(i).getCommID().getCode());
			model.setName(childrenOfSub.get(i).getCommID().getName());
			childrenModel.put("child number:"+(i).toString(),model);
		}
		return childrenModel;
		
	}
	
	@Override
	@Transactional
	public Map<String,Object> getTheChildrenOfParent(String parentCode){
		EmpStructParent parent = employeeDAO.getParent(parentCode);
		List<EmpStructChild> childrenOfParent = parent.getChildren();
		Map<String,Object> childrenModel =new HashMap<String,Object>();
		if(childrenOfParent!=null) {
			for(Integer i=0;i<childrenOfParent.size();i++) {
				EmployeeStructModel model = new EmployeeStructModel();
				DateFormat dateFormat = new SimpleDateFormat();
				
				String startDate = dateFormat.format(childrenOfParent.get(i).getCommID().getStartDate());
				String endDate = dateFormat.format(childrenOfParent.get(i).getCommID().getEndDate());
				
				model.setStartDate(startDate);
				model.setEndDate(endDate);
				model.setCode(childrenOfParent.get(i).getCommID().getCode());
				model.setName(childrenOfParent.get(i).getCommID().getName());
				childrenModel.put("child number:"+(i).toString(),model);
			}
		}
		return childrenModel;
		
	}
	
	@Override
	@Transactional
	public EmpStructParent getParent(String code) {
		EmpStructParent parent = employeeDAO.getParent(code);
		return parent;
	}
	
	@Override
	@Transactional
	public EmpStructSubparent getSubParent(String code){
		EmpStructSubparent subParent = employeeDAO.getSubParent(code);
		return subParent;	
	}
	
}
