package com.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
			commId.setDeleted(0);
			
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
				
			}
			return savedEmployeeStruct.toString();
		}
		else {
			return "values is missing for saving the employee struct";
		}
		
	}
	
	@Override
	@Transactional
	public List<EmployeeStructModel> getTheSubParentsOfSubParent(String parentCode){
		EmpStructSubparent subParent = employeeDAO.getSubParent(parentCode);
		List<EmployeeStructModel> listOfSubParents = new ArrayList<EmployeeStructModel>();
		if(subParent.getCommID().getDeleted()==0){
			List<EmpStructSubparent> subParents = employeeDAO.getSubParentsOfSubParents(parentCode);
			
			if(subParents!=null) {
				for(Integer i = 0;i<subParents.size();i++) {
					//create a model
					EmployeeStructModel model = new EmployeeStructModel();
					DateFormat dateFormat = new SimpleDateFormat();

					String startDate = dateFormat.format(subParents.get(i).getCommID().getStartDate());
					String endDate = dateFormat.format(subParents.get(i).getCommID().getEndDate());
					
					model.setParentCode(parentCode);
					model.setHasChild(true);
					model.setHasParent(true);
					model.setStartDate(startDate);
					model.setEndDate(endDate);
					model.setCode(subParents.get(i).getCommID().getCode());
					model.setName(subParents.get(i).getCommID().getName());				
					listOfSubParents.add(model);
					
					if(subParents.get(i).getChildren()!=null) {
						listOfSubParents.addAll(getTheChildrenOfSubParent(subParents.get(i).getCommID().getCode()));
					}
					
					if(employeeDAO.getSubParentsOfSubParents(subParents.get(i).getCommID().getCode())!=null) {

						listOfSubParents.addAll(getTheSubParentsOfSubParent(subParents.get(i).getCommID().getCode()));
					}
				}
			}
		}
		return listOfSubParents;
	}
	
	@Override
	@Transactional
	public EmployeeStructModel getTheParent(String code){
		EmpStructParent parent = employeeDAO.getParent(code);
		EmployeeStructModel model = new EmployeeStructModel();
		if(parent.getCommID().getDeleted()==0){
			DateFormat dateFormat = new SimpleDateFormat();
					
			String startDate = dateFormat.format(parent.getCommID().getStartDate());
			String endDate = dateFormat.format(parent.getCommID().getEndDate());
			
			model.setHasChild(true);
			model.setHasParent(false);
			model.setParentCode(null);
			model.setStartDate(startDate);
			model.setEndDate(endDate);
			model.setCode(parent.getCommID().getCode());
			model.setName(parent.getCommID().getName());
			}
		return model;
		}
		
	@Override
	@Transactional
	public EmployeeStructModel getTheSubParent(String code){
		EmpStructSubparent subParent = employeeDAO.getSubParent(code);		
		EmployeeStructModel model = new EmployeeStructModel();
		if(subParent.getCommID().getDeleted()==0){
			DateFormat dateFormat = new SimpleDateFormat();
					
			String startDate = dateFormat.format(subParent.getCommID().getStartDate());
			String endDate = dateFormat.format(subParent.getCommID().getEndDate());
			
			model.setHasChild(true);
			model.setHasParent(true);
			model.setParentCode(subParent.getParentCode());
			model.setStartDate(startDate);
			model.setEndDate(endDate);
			model.setCode(subParent.getCommID().getCode());
			model.setName(subParent.getCommID().getName());
		}
		return model;
		}
	
	@Override
	@Transactional
	public EmployeeStructModel getTheChild(String code){
		EmpStructChild child = employeeDAO.getChild(code);	
		EmployeeStructModel model = new EmployeeStructModel();
		if(child.getCommID().getDeleted()==0){
			DateFormat dateFormat = new SimpleDateFormat();
					
			String startDate = dateFormat.format(child.getCommID().getStartDate());
			String endDate = dateFormat.format(child.getCommID().getEndDate());
			
			EmpStructParent hisParentIsParent =child.getParent();
			EmpStructSubparent hisParentIsSub=child.getSubParent();
			model.setHasChild(false);
			model.setHasParent(true);
			
			if(hisParentIsParent!=null) {
			model.setParentCode(child.getParent().getCommID().getCode());
			}else if (hisParentIsSub!=null) {
				model.setParentCode(child.getSubParent().getCommID().getCode());
			}
			
			model.setStartDate(startDate);
			model.setEndDate(endDate);
			model.setCode(child.getCommID().getCode());
			model.setName(child.getCommID().getName());
			}
		return model;
		}
	
	@Override
	@Transactional
	public List<EmployeeStructModel> getTheSubParentsOfParent(String code){
		EmpStructParent parent =employeeDAO.getParent(code);
		
		List<EmpStructSubparent> subParentsOfParent = parent.getSubParents();
		List<EmployeeStructModel> listOfSubParents = new ArrayList<EmployeeStructModel>();		
		if(subParentsOfParent!=null) {
			for(Integer i=0;i<subParentsOfParent.size();i++) {
				EmployeeStructModel model = new EmployeeStructModel();
				if(subParentsOfParent.get(i).getCommID().getDeleted()==0){
					DateFormat dateFormat = new SimpleDateFormat();
					
					String startDate = dateFormat.format(subParentsOfParent.get(i).getCommID().getStartDate());
					String endDate = dateFormat.format(subParentsOfParent.get(i).getCommID().getEndDate());
					
					model.setHasParent(true);
					model.setParentCode(code);
					model.setHasChild(true);
					
					model.setStartDate(startDate);
					model.setEndDate(endDate);
					model.setCode(subParentsOfParent.get(i).getCommID().getCode());
					model.setName(subParentsOfParent.get(i).getCommID().getName());
		
					listOfSubParents.add(model);}
			}
		}
		return listOfSubParents;
	}
		
	@Override
	@Transactional
	public List<EmployeeStructModel> getTheChildrenOfSubParent(String subCode){
		EmpStructSubparent subParent = employeeDAO.getSubParent(subCode);
		List<EmpStructChild> childrenOfSub = subParent.getChildren();
		List<EmployeeStructModel> listOfChildren = new ArrayList<EmployeeStructModel>() ; 
		if(childrenOfSub!=null) {
			for(Integer i=0;i<childrenOfSub.size();i++) {
				EmployeeStructModel model = new EmployeeStructModel();
				if(childrenOfSub.get(i).getCommID().getDeleted()==0){
					DateFormat dateFormat = new SimpleDateFormat();
					
					String startDate = dateFormat.format(childrenOfSub.get(i).getCommID().getStartDate());
					String endDate = dateFormat.format(childrenOfSub.get(i).getCommID().getEndDate());
					
					model.setHasParent(true);
					model.setParentCode(subCode);
					model.setHasChild(false);
					
					model.setStartDate(startDate);
					model.setEndDate(endDate);
					model.setCode(childrenOfSub.get(i).getCommID().getCode());
					model.setName(childrenOfSub.get(i).getCommID().getName());
					listOfChildren.add(model);}
			}
		}
		
		return listOfChildren;
		
	}
	
	@Override
	@Transactional
	public List<EmployeeStructModel> getTheChildrenOfParent(String parentCode){
		EmpStructParent parent = employeeDAO.getParent(parentCode);
		List<EmpStructChild> childrenOfParent = parent.getChildren();
		List<EmployeeStructModel> listOfChildren = new ArrayList<EmployeeStructModel>() ; 
		if(childrenOfParent!=null) {
			for(Integer i=0;i<childrenOfParent.size();i++) {
				EmployeeStructModel model = new EmployeeStructModel();
				if(childrenOfParent.get(i).getCommID().getDeleted()==0){
					DateFormat dateFormat = new SimpleDateFormat();
					
					String startDate = dateFormat.format(childrenOfParent.get(i).getCommID().getStartDate());
					String endDate = dateFormat.format(childrenOfParent.get(i).getCommID().getEndDate());
					
					model.setHasParent(true);
					model.setParentCode(parentCode);
					model.setHasChild(false);
					
					model.setStartDate(startDate);
					model.setEndDate(endDate);
					model.setCode(childrenOfParent.get(i).getCommID().getCode());
					model.setName(childrenOfParent.get(i).getCommID().getName());
					listOfChildren.add(model);}
			}
		}
		return listOfChildren;
		
	}
	
	@Override
	@Transactional
	public EmployeeStructModel getParentOfSub(EmpStructSubparent sub) {
		EmployeeStructModel parentOfSub;		
		if(sub.getHasParent()==0) {
			EmpStructParent parent = sub.getParent();
			parentOfSub = (getTheParent(parent.getCommID().getCode()));
		}
		else {
			EmpStructSubparent subParent =employeeDAO.getSubParent(sub.getParentCode());	
			parentOfSub = (getTheSubParent(subParent.getCommID().getCode()));
		}
		return parentOfSub;
	}
	
	@Override
	@Transactional
	public EmployeeStructModel getParentOfChild(EmpStructChild child){
		EmployeeStructModel parentOfChild = null;
		if(child.getParent()!=null) {
			EmployeeStructModel parent =getTheParent(child.getParent().getCommID().getCode());
			parentOfChild= parent;
		}else if(child.getSubParent()!=null) {
			EmployeeStructModel sub= getTheSubParent(child.getSubParent().getCommID().getCode());
			parentOfChild = sub;
		}
		
		return parentOfChild;
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
	
	@Override
	@Transactional
	public Boolean isSubParent(String parentCode) {
		Boolean isSub = employeeDAO.isSubParent(parentCode);
		return isSub;
	}
	
	@Override
	@Transactional
	public Boolean isParent(String parentCode) {
		Boolean isParent = employeeDAO.isParent(parentCode);
		return isParent;
	}
	
	/*
	 * Getting The Chain 
	 * */
	@Override
	@Transactional
	public List<EmployeeStructModel> getParentChain(String code){
		List<EmployeeStructModel> parentMap = new ArrayList<EmployeeStructModel>();
		//getting parent
		EmployeeStructModel parent = getTheParent(code);
		EmpStructParent parentObject = getParent(code);
		if(parentObject.getCommID().getDeleted()==0){
			List<EmployeeStructModel> subOfParent =getTheSubParentsOfParent(parentObject.getCommID().getCode());
			List<EmployeeStructModel> childrenOfParent =getTheChildrenOfParent(parentObject.getCommID().getCode());
			List<EmpStructSubparent> subOfParentObject = parentObject.getSubParents();
			
			for(Integer i = 0; i<subOfParentObject.size();i++) {
				List<EmployeeStructModel> subOfSub =getTheSubParentsOfSubParent(subOfParentObject.get(i).getCommID().getCode());
				List<EmployeeStructModel> childrenOfSubMap =getTheChildrenOfSubParent(subOfParentObject.get(i).getCommID().getCode());
					
				parentMap.addAll(childrenOfSubMap);
				parentMap.addAll(subOfSub);
			}			
			parentMap.add(parent);
			parentMap.addAll(subOfParent);
			parentMap.addAll(childrenOfParent);}
		return parentMap;		
	}
	
	@Override
	@Transactional
	public List<EmployeeStructModel> getSubParentChain(String code){
		List<EmployeeStructModel>subparentChainMap = new ArrayList<EmployeeStructModel>();
		EmployeeStructModel subParent = getTheSubParent(code);
		EmpStructSubparent subObject = employeeDAO.getSubParent(code);
		if(subObject.getCommID().getDeleted()==0){
			subparentChainMap.add(subParent);
			
			subparentChainMap.addAll(getTheChildrenOfSubParent(code));
			  
			List<EmployeeStructModel> subOfSub	=getTheSubParentsOfSubParent(subObject.getCommID().getCode());
			subparentChainMap.addAll(subOfSub);
			  
			while(subObject.getHasParent()!=0) {
			subparentChainMap.add(getParentOfSub(subObject)); 
			subObject =	employeeDAO.getSubParent(subObject.getParentCode());
			}
			subparentChainMap.add(getParentOfSub(subObject));}
		
		return subparentChainMap;
	}

	@Override
	@Transactional
	public List<EmployeeStructModel> getChildChain(String code){
		List<EmployeeStructModel> childChainMap = new ArrayList<EmployeeStructModel>();
		
		//getting child
		EmployeeStructModel child = getTheChild(code);
		childChainMap.add(child);
		EmpStructChild childObject = employeeDAO.getChild(code);
		if(childObject.getCommID().getDeleted()==0){
			EmpStructParent hisParent=childObject.getParent();
			EmpStructSubparent hisSubParent= childObject.getSubParent();
			if(hisParent!=null) {
				childChainMap.add(getTheChild(childObject.getParent().getCommID().getCode()));
			}else if(hisSubParent!=null) {
				childChainMap.add(getTheSubParent(hisSubParent.getCommID().getCode()));
				while(hisSubParent.getHasParent()!=0) {
					childChainMap.add(getParentOfSub(hisSubParent)); 
					hisSubParent =	employeeDAO.getSubParent(hisSubParent.getParentCode());
				}childChainMap.add(getParentOfSub(hisSubParent));
			}
		}
		return childChainMap;
	
	}
	
	/*
	 * process the update employeeStructure
	 * 
	 * */
	@Override
	@Transactional
	public String updateEmployeeStructure(EmployeeStructModel employee) {
		Boolean updateTheEmpStruct = false;
		try {
			//process to check the model(parent/sub/child)
			if (employee.getName()!= null && 
					employee.getCode()!=null&&
						employee.getEndDate()!=null&&
							employee.getStartDate()!=null&&
								employee.getHasParent()!=null&&
									employee.getHasChild()!=null){
				//conversion of dates 
				Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(employee.getStartDate());  
				Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(employee.getEndDate());
				String parentCode =employee.getParentCode();
				
				//check if this model has parent and type of his parent
				Boolean hisParentIsParent = employeeDAO.isParent(parentCode);
				Boolean hisParentIsSubParent = employeeDAO.isSubParent(parentCode);
				
				if(!employee.getHasParent() && employee.getHasChild()) {
					//the model has no parent but has a child ==> update parent
					EmpStructParent parent = employeeDAO.getParent(employee.getCode());
					
					parent.getCommID().setName(employee.getName());
					parent.getCommID().setStartDate(startDate);
					parent.getCommID().setEndDate(endDate);
					updateTheEmpStruct = employeeDAO.addParent(parent);
				}else if(employee.getHasParent() && employee.getHasChild()
						 && hisParentIsParent) {
					//the model has parent also has child and his parent is parent ==> save as subParent 
					EmpStructSubparent subParent = employeeDAO.getSubParent(employee.getCode());
					
					subParent.getCommID().setName(employee.getName());
					subParent.getCommID().setStartDate(startDate);
					subParent.getCommID().setEndDate(endDate);
					updateTheEmpStruct = employeeDAO.addSubParentToParent(subParent, parentCode);
				}else if( employee.getHasParent() && employee.getHasChild()
						 && hisParentIsSubParent) {
					EmpStructSubparent subParent = employeeDAO.getSubParent(employee.getCode());
					
					subParent.getCommID().setName(employee.getName());
					subParent.getCommID().setStartDate(startDate);
					subParent.getCommID().setEndDate(endDate);
					updateTheEmpStruct = employeeDAO.addSubParentToSubParent(subParent);
				}else if(employee.getHasParent() && !employee.getHasChild()
						 && hisParentIsParent) {
					//the model has parent and has no child and his parent is parent ==>save as child to parent
					EmpStructChild child =employeeDAO.getChild(employee.getCode());
					
					child.getCommID().setName(employee.getName());
					child.getCommID().setStartDate(startDate);
					child.getCommID().setEndDate(endDate);
					updateTheEmpStruct = employeeDAO.addChildToParent(child, parentCode);
				}else if(employee.getHasParent() && !employee.getHasChild()
						 && hisParentIsSubParent) {
					//the model has parent and has no child and his parent is subParent ==>save as child to subParent
					EmpStructChild child =employeeDAO.getChild(employee.getCode());
					
					child.getCommID().setName(employee.getName());
					child.getCommID().setStartDate(startDate);
					child.getCommID().setEndDate(endDate);
					updateTheEmpStruct =employeeDAO.addChildToSubParent(child,parentCode);
				}
			}
			return updateTheEmpStruct.toString();
		}catch(Exception e) {return "false";}
			
	}
	
	@Override
	@Transactional
	public List<EmpStructSubparent> getSubOfSub(String code){
		EmpStructSubparent subParent = employeeDAO.getSubParent(code);
		List<EmpStructSubparent> listOfSubParents = new ArrayList<EmpStructSubparent>();
		List<EmpStructSubparent> subParents = employeeDAO.getSubParentsOfSubParents(subParent.getCommID().getCode());
		if(subParents!=null) {
			for(Integer i = 0;i<subParents.size();i++) {
				listOfSubParents.add(subParents.get(i));
				
				if(employeeDAO.getSubParentsOfSubParents(subParents.get(i).getCommID().getCode())!=null) {
					listOfSubParents.addAll(getSubOfSub(subParents.get(i).getCommID().getCode()));
				}
			}
		}
		return listOfSubParents;	
	}
	
	@Override
	@Transactional
	public String deleteParent(String code) {
		EmpStructParent parent = getParent(code);
		List<EmpStructSubparent> subs = parent.getSubParents();
		List<EmpStructSubparent> subOfsub=new ArrayList<EmpStructSubparent>();
		String isDeleted ="false";
		for(int i =0;i<subs.size();i++) {
			subOfsub.addAll(getSubOfSub(subs.get(i).getCommID().getCode()));
		}
		for(int i=0;i<subOfsub.size();i++) {
			isDeleted=employeeDAO.deleteSubParent(subOfsub.get(i).getCommID().getCode());
		}
		isDeleted = employeeDAO.deleteParent(code);
		return isDeleted;
	}

	
	@Override
	@Transactional
	public String deleteSubParent(String code) {
		EmpStructSubparent sub =employeeDAO.getSubParent(code);
		String isDeleted ="false";
		List<EmpStructSubparent> subParents = getSubOfSub(code);
		isDeleted = employeeDAO.deleteSubParent(sub.getCommID().getCode());
		for(int i=0;i<subParents.size();i++) {
			isDeleted=employeeDAO.deleteSubParent(subParents.get(i).getCommID().getCode());
		}
		return isDeleted;		
	}
	

	@Override
	@Transactional
	public String deleteChild(String code) {
		String isDeleted ="false";
		isDeleted =employeeDAO.deleteChild(code);
		return isDeleted;
	}

	@Override
	@Transactional
	public void delmitParent(String code) {
		EmpStructParent parent =employeeDAO.getParent(code);
		parent.getCommID().setDeleted(1);
		List<EmpStructSubparent> subParents = parent.getSubParents();
		List<EmpStructChild> children =parent.getChildren();
		for(int i=0;i<subParents.size();i++) {
			delmitSubParent(subParents.get(i).getCommID().getCode());
		}
		for(int i=0;i<children.size();i++) {
			delmitChild(children.get(i).getCommID().getCode());
		}
	}

	@Override
	@Transactional
	public void delmitSubParent(String code) {
		EmpStructSubparent sub = employeeDAO.getSubParent(code);
		sub.getCommID().setDeleted(1);
		List<EmpStructChild> child = sub.getChildren();
		for(int i=0;i<child.size();i++) {
			child.get(i).getCommID().setDeleted(1);
		}
		List<EmpStructSubparent> subParents = getSubOfSub(code);
		for(int i=0;i<subParents.size();i++) {
			subParents.get(i).getCommID().setDeleted(1);
			List<EmpStructChild> children = subParents.get(i).getChildren();
			for(int j=0;j<children.size();j++) {
				children.get(j).getCommID().setDeleted(1);
			}
		}
	}

	@Override
	@Transactional
	public void delmitChild(String code) {
		EmpStructChild child =employeeDAO.getChild(code);
		child.getCommID().setDeleted(1);
	}
	
	
	

}
