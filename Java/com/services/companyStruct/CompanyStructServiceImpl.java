package com.services.companyStruct;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.companyStruct.CompanyStructDAO;
import com.entities.companyStruct.CompanyCommonID;
import com.entities.companyStruct.CompanyStructChild;
import com.entities.companyStruct.CompanyStructParent;
import com.entities.companyStruct.CompanyStructSubparent;
import com.models.companyStruct.CompanyStructModel;

@Service
public class CompanyStructServiceImpl implements CompanyStructService {

	@Autowired
	private CompanyStructDAO companyDAO;

	@Override
	@Transactional
	public void processTheIncommingModel(CompanyStructModel company) {

		if (company.getName() != null && company.getCode() != null && company.getEndDate() != null
				&& company.getStartDate() != null && company.getHasParent() != null
				&& company.getHasChild() != null) {

			// conversion of dates
			Date startDate;
			Date endDate;
			CompanyCommonID commId = new CompanyCommonID();
			try {
				startDate = new SimpleDateFormat("dd/MM/yyyy").parse(company.getStartDate());
				endDate = new SimpleDateFormat("dd/MM/yyyy").parse(company.getEndDate());

				// commonID data
				commId = new CompanyCommonID(startDate, endDate, company.getCode(), company.getName());
				commId.setDeleted(0);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String parentCode = company.getParentCode();

			// check if this model has parent and type of his parent
			Boolean hisParentIsParent = companyDAO.isParent(parentCode);
			Boolean hisParentIsSubParent = companyDAO.isSubParent(parentCode);

			// Process the model to know if it is a parent/SubParent/Child
			if (!company.getHasParent() && company.getHasChild()) {

				// the model has no parent but has a child ==> save as parent
				CompanyStructParent parent = new CompanyStructParent(commId);
				companyDAO.addParent(parent);

			} else if (company.getHasParent() && company.getHasChild() && hisParentIsParent) {

				// the model has parent also has child and his parent is parent ==> save as
				// subParent
				CompanyStructSubparent subParent = new CompanyStructSubparent(0, null, commId);
				companyDAO.addSubParentToParent(subParent, parentCode);

			} else if (company.getHasParent() && company.getHasChild() && hisParentIsSubParent) {

				// the model has parent also has child and his parent is subParent ==> save as
				// subParent
				CompanyStructSubparent subParent = new CompanyStructSubparent(1, parentCode, commId);
				companyDAO.addSubParentToSubParent(subParent);

			} else if (company.getHasParent() && !company.getHasChild() && hisParentIsParent) {

				// the model has parent and has no child and his parent is parent ==>save as
				// child to parent
				CompanyStructChild child = new CompanyStructChild(commId);
				companyDAO.addChildToParent(child, parentCode);

			} else if (company.getHasParent() && !company.getHasChild() && hisParentIsSubParent) {

				// the model has parent and has no child and his parent is subParent ==>save as
				// child to subParent
				CompanyStructChild child = new CompanyStructChild(commId);
				companyDAO.addChildToSubParent(child, parentCode);

			}
		
		}

	}

	@Override
	@Transactional
	public List<CompanyStructModel> getTheSubParentsOfSubParent(String parentCode) {
		List<CompanyStructModel> listOfSubParents = new ArrayList<CompanyStructModel>();
		List<CompanyStructSubparent> subParents = companyDAO.getSubParentsOfSubParents(parentCode);
		if (subParents != null) {
			for (Integer i = 0; i < subParents.size(); i++) {
				// create a model
				CompanyStructModel model = new CompanyStructModel();
				DateFormat dateFormat = new SimpleDateFormat();

				String startDate = dateFormat.format(subParents.get(i).getCommID().getStartDate());
				String endDate = dateFormat.format(subParents.get(i).getCommID().getEndDate());

				model.setParentCode(parentCode);
				model.setHasChild(true);
				model.setHasParent(true);
				model.setStartDate(startDate.substring(0, 6));
				model.setEndDate(endDate.substring(0, 6));
				model.setCode(subParents.get(i).getCommID().getCode());
				model.setName(subParents.get(i).getCommID().getName());
				listOfSubParents.add(model);

				if (subParents.get(i).getChildren() != null) {
					listOfSubParents.addAll(getTheChildrenOfSubParent(subParents.get(i).getCommID().getCode()));
				}

				if (companyDAO.getSubParentsOfSubParents(subParents.get(i).getCommID().getCode()) != null) {
					listOfSubParents.addAll(getTheSubParentsOfSubParent(subParents.get(i).getCommID().getCode()));
				}
			}
		}
		return listOfSubParents;
	}

	@Override
	@Transactional
	public CompanyStructModel getTheParent(String code) {
		CompanyStructParent parent = companyDAO.getParent(code);
		CompanyStructModel model = new CompanyStructModel();
		DateFormat dateFormat = new SimpleDateFormat();

		String startDate = dateFormat.format(parent.getCommID().getStartDate());
		String endDate = dateFormat.format(parent.getCommID().getEndDate());

		model.setHasChild(true);
		model.setHasParent(false);
		model.setParentCode(null);
		model.setStartDate(startDate.substring(0, 6));
		model.setEndDate(endDate.substring(0, 6));
		model.setCode(parent.getCommID().getCode());
		model.setName(parent.getCommID().getName());

		return model;
	}

	@Override
	@Transactional
	public CompanyStructModel getTheSubParent(String code) {
		CompanyStructSubparent subParent = companyDAO.getSubParent(code);
		CompanyStructModel model = new CompanyStructModel();
		DateFormat dateFormat = new SimpleDateFormat();

		String startDate = dateFormat.format(subParent.getCommID().getStartDate());
		String endDate = dateFormat.format(subParent.getCommID().getEndDate());
		CompanyStructParent parent = subParent.getParent();
		model.setHasChild(true);
		model.setHasParent(true);
		if (parent != null) {
			model.setParentCode(parent.getCommID().getCode());
		} else {
			model.setParentCode(subParent.getParentCode());
		}
		model.setStartDate(startDate.substring(0, 6));
		model.setEndDate(endDate.substring(0, 6));
		model.setCode(subParent.getCommID().getCode());
		model.setName(subParent.getCommID().getName());

		return model;
	}

	@Override
	@Transactional
	public CompanyStructModel getTheChild(String code) {
		CompanyStructChild child = companyDAO.getChild(code);
		CompanyStructModel model = new CompanyStructModel();
		DateFormat dateFormat = new SimpleDateFormat();

		String startDate = dateFormat.format(child.getCommID().getStartDate());
		String endDate = dateFormat.format(child.getCommID().getEndDate());

		CompanyStructParent hisParentIsParent = child.getParent();
		CompanyStructSubparent hisParentIsSub = child.getSubParent();
		model.setHasChild(false);
		model.setHasParent(true);

		if (hisParentIsParent != null) {
			model.setParentCode(child.getParent().getCommID().getCode());
		} else if (hisParentIsSub != null) {
			model.setParentCode(child.getSubParent().getCommID().getCode());
		}

		model.setStartDate(startDate.substring(0, 6));
		model.setEndDate(endDate.substring(0, 6));
		model.setCode(child.getCommID().getCode());
		model.setName(child.getCommID().getName());

		return model;
	}

	@Override
	@Transactional
	public List<CompanyStructModel> getTheSubParentsOfParent(String code) {
		CompanyStructParent parent = companyDAO.getParent(code);

		List<CompanyStructSubparent> subParentsOfParent = parent.getSubParents();
		List<CompanyStructModel> listOfSubParents = new ArrayList<CompanyStructModel>();
		if (subParentsOfParent != null) {
			for (Integer i = 0; i < subParentsOfParent.size(); i++) {
				CompanyStructModel model = new CompanyStructModel();
				DateFormat dateFormat = new SimpleDateFormat();

				String startDate = dateFormat.format(subParentsOfParent.get(i).getCommID().getStartDate());
				String endDate = dateFormat.format(subParentsOfParent.get(i).getCommID().getEndDate());

				model.setHasParent(true);
				model.setParentCode(code);
				model.setHasChild(true);

				model.setStartDate(startDate.substring(0, 6));
				model.setEndDate(endDate.substring(0, 6));
				model.setCode(subParentsOfParent.get(i).getCommID().getCode());
				model.setName(subParentsOfParent.get(i).getCommID().getName());

				listOfSubParents.add(model);

			}
		}
		return listOfSubParents;
	}

	@Override
	@Transactional
	public List<CompanyStructModel> getTheChildrenOfSubParent(String subCode) {
		CompanyStructSubparent subParent = companyDAO.getSubParent(subCode);
		List<CompanyStructChild> childrenOfSub = subParent.getChildren();
		List<CompanyStructModel> listOfChildren = new ArrayList<CompanyStructModel>();
		if (childrenOfSub != null) {
			for (Integer i = 0; i < childrenOfSub.size(); i++) {
				CompanyStructModel model = new CompanyStructModel();
				DateFormat dateFormat = new SimpleDateFormat();

				String startDate = dateFormat.format(childrenOfSub.get(i).getCommID().getStartDate());
				String endDate = dateFormat.format(childrenOfSub.get(i).getCommID().getEndDate());

				model.setHasParent(true);
				model.setParentCode(subCode);
				model.setHasChild(false);

				model.setStartDate(startDate.substring(0, 6));
				model.setEndDate(endDate.substring(0, 6));
				model.setCode(childrenOfSub.get(i).getCommID().getCode());
				model.setName(childrenOfSub.get(i).getCommID().getName());
				listOfChildren.add(model);

			}
		}

		return listOfChildren;

	}

	@Override
	@Transactional
	public List<CompanyStructModel> getTheChildrenOfParent(String parentCode) {
		CompanyStructParent parent = companyDAO.getParent(parentCode);
		List<CompanyStructChild> childrenOfParent = parent.getChildren();
		List<CompanyStructModel> listOfChildren = new ArrayList<CompanyStructModel>();
		if (childrenOfParent != null) {
			for (Integer i = 0; i < childrenOfParent.size(); i++) {
				CompanyStructModel model = new CompanyStructModel();
				DateFormat dateFormat = new SimpleDateFormat();

				String startDate = dateFormat.format(childrenOfParent.get(i).getCommID().getStartDate());
				String endDate = dateFormat.format(childrenOfParent.get(i).getCommID().getEndDate());

				model.setHasParent(true);
				model.setParentCode(parentCode);
				model.setHasChild(false);

				model.setStartDate(startDate.substring(0, 6));
				model.setEndDate(endDate.substring(0, 6));
				model.setCode(childrenOfParent.get(i).getCommID().getCode());
				model.setName(childrenOfParent.get(i).getCommID().getName());
				listOfChildren.add(model);

			}
		}
		return listOfChildren;

	}

	@Override
	@Transactional
	public CompanyStructModel getParentOfSub(CompanyStructSubparent sub) {
		CompanyStructModel parentOfSub;
		if (sub.getHasParent() == 0) {
			CompanyStructParent parent = sub.getParent();
			parentOfSub = (getTheParent(parent.getCommID().getCode()));
		} else {
			CompanyStructSubparent subParent = companyDAO.getSubParent(sub.getParentCode());
			parentOfSub = (getTheSubParent(subParent.getCommID().getCode()));
		}
		return parentOfSub;
	}

	@Override
	@Transactional
	public CompanyStructModel getParentOfChild(CompanyStructChild child) {
		CompanyStructModel parentOfChild = null;
		if (child.getParent() != null) {
			CompanyStructModel parent = getTheParent(child.getParent().getCommID().getCode());
			parentOfChild = parent;
		} else if (child.getSubParent() != null) {
			CompanyStructModel sub = getTheSubParent(child.getSubParent().getCommID().getCode());
			parentOfChild = sub;
		}

		return parentOfChild;
	}

	@Override
	@Transactional
	public CompanyStructParent getParent(String code) {
		CompanyStructParent parent = companyDAO.getParent(code);
		return parent;
	}

	@Override
	@Transactional
	public CompanyStructSubparent getSubParent(String code) {
		CompanyStructSubparent subParent = companyDAO.getSubParent(code);
		return subParent;
	}

	@Override
	@Transactional
	public Boolean isSubParent(String parentCode) {
		Boolean isSub = companyDAO.isSubParent(parentCode);
		return isSub;
	}

	@Override
	@Transactional
	public Boolean isParent(String parentCode) {
		Boolean isParent = companyDAO.isParent(parentCode);
		return isParent;
	}

	/*
	 * Getting The Chain
	 */
	@Override
	@Transactional
	public List<CompanyStructModel> getParentChain(String code) {
		List<CompanyStructModel> parentMap = new ArrayList<CompanyStructModel>();
		// getting parent
		CompanyStructModel parent = getTheParent(code);
		CompanyStructParent parentObject = getParent(code);
		List<CompanyStructModel> subOfParent = getTheSubParentsOfParent(parentObject.getCommID().getCode());
		List<CompanyStructModel> childrenOfParent = getTheChildrenOfParent(parentObject.getCommID().getCode());
		List<CompanyStructSubparent> subOfParentObject = parentObject.getSubParents();

		for (Integer i = 0; i < subOfParentObject.size(); i++) {
			List<CompanyStructModel> subOfSub = getTheSubParentsOfSubParent(
					subOfParentObject.get(i).getCommID().getCode());
			List<CompanyStructModel> childrenOfSubMap = getTheChildrenOfSubParent(
					subOfParentObject.get(i).getCommID().getCode());

			parentMap.addAll(childrenOfSubMap);
			parentMap.addAll(subOfSub);
		}
		parentMap.add(parent);
		parentMap.addAll(subOfParent);
		parentMap.addAll(childrenOfParent);

		return parentMap;
	}

	@Override
	@Transactional
	public List<CompanyStructModel> getSubParentChain(String code) {
		List<CompanyStructModel> subparentChainMap = new ArrayList<CompanyStructModel>();
		CompanyStructModel subParent = getTheSubParent(code);
		CompanyStructSubparent subObject = companyDAO.getSubParent(code);
		subparentChainMap.add(subParent);

		subparentChainMap.addAll(getTheChildrenOfSubParent(code));

		List<CompanyStructModel> subOfSub = getTheSubParentsOfSubParent(subObject.getCommID().getCode());
		subparentChainMap.addAll(subOfSub);

		while (subObject.getHasParent() != 0) {
			subparentChainMap.add(getParentOfSub(subObject));
			subObject = companyDAO.getSubParent(subObject.getParentCode());
		}
		subparentChainMap.add(getParentOfSub(subObject));

		return subparentChainMap;
	}

	@Override
	@Transactional
	public List<CompanyStructModel> getChildChain(String code) {
		List<CompanyStructModel> childChainMap = new ArrayList<CompanyStructModel>();

		// getting child
		CompanyStructModel child = getTheChild(code);
		childChainMap.add(child);
		CompanyStructChild childObject = companyDAO.getChild(code);
		CompanyStructParent hisParent = childObject.getParent();
		CompanyStructSubparent hisSubParent = childObject.getSubParent();
		if (hisParent != null) {
			childChainMap.add(getTheChild(childObject.getParent().getCommID().getCode()));
		} else if (hisSubParent != null) {
			childChainMap.add(getTheSubParent(hisSubParent.getCommID().getCode()));
			while (hisSubParent.getHasParent() != 0) {
				childChainMap.add(getParentOfSub(hisSubParent));
				hisSubParent = companyDAO.getSubParent(hisSubParent.getParentCode());
			}
			childChainMap.add(getParentOfSub(hisSubParent));
		}

		return childChainMap;

	}

	/*
	 * process the update CompanyStructure
	 * 
	 */
	@Override
	@Transactional
	public void updateCompanyStructure(CompanyStructModel employee) throws ParseException {
		// process to check the model(parent/sub/child)
		if (employee.getName() != null && employee.getCode() != null && employee.getEndDate() != null
					&& employee.getStartDate() != null && employee.getHasParent() != null
					&& employee.getHasChild() != null) {
			// conversion of dates
			Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(employee.getStartDate());
			Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(employee.getEndDate());
			String parentCode = employee.getParentCode();

			// check if this model has parent and type of his parent
			Boolean hisParentIsParent = companyDAO.isParent(parentCode);
			Boolean hisParentIsSubParent = companyDAO.isSubParent(parentCode);

			if (!employee.getHasParent() && employee.getHasChild()) {
				// the model has no parent but has a child ==> update parent
				CompanyStructParent parent = companyDAO.getParent(employee.getCode());

				parent.getCommID().setName(employee.getName());
				parent.getCommID().setStartDate(startDate);
				parent.getCommID().setEndDate(endDate);
				companyDAO.addParent(parent);
			} else if (employee.getHasParent() && employee.getHasChild() && hisParentIsParent) {
				// the model has parent also has child and his parent is parent ==> save as
				// subParent
				CompanyStructSubparent subParent = companyDAO.getSubParent(employee.getCode());
				subParent.getCommID().setName(employee.getName());
				subParent.getCommID().setStartDate(startDate);
				subParent.getCommID().setEndDate(endDate);
				companyDAO.addSubParentToParent(subParent, parentCode);
			} else if (employee.getHasParent() && employee.getHasChild() && hisParentIsSubParent) {
				CompanyStructSubparent subParent = companyDAO.getSubParent(employee.getCode());
				subParent.getCommID().setName(employee.getName());
				subParent.getCommID().setStartDate(startDate);
				subParent.getCommID().setEndDate(endDate);
				companyDAO.addSubParentToSubParent(subParent);
			} else if (employee.getHasParent() && !employee.getHasChild() && hisParentIsParent) {
				// the model has parent and has no child and his parent is parent ==>save as
				// child to parent
				CompanyStructChild child = companyDAO.getChild(employee.getCode());
				child.getCommID().setName(employee.getName());
				child.getCommID().setStartDate(startDate);
				child.getCommID().setEndDate(endDate);
				companyDAO.addChildToParent(child, parentCode);
			} else if (employee.getHasParent() && !employee.getHasChild() && hisParentIsSubParent) {
				// the model has parent and has no child and his parent is subParent ==>save as
				// child to subParent
				CompanyStructChild child = companyDAO.getChild(employee.getCode());

				child.getCommID().setName(employee.getName());
				child.getCommID().setStartDate(startDate);
				child.getCommID().setEndDate(endDate);
				companyDAO.addChildToSubParent(child, parentCode);
			}
		}
	}

	@Override
	@Transactional
	public List<CompanyStructSubparent> getSubOfSub(String code) {
		CompanyStructSubparent subParent = companyDAO.getSubParent(code);
		List<CompanyStructSubparent> listOfSubParents = new ArrayList<CompanyStructSubparent>();
		List<CompanyStructSubparent> subParents = companyDAO.getSubParentsOfSubParents(subParent.getCommID().getCode());
		if (subParents != null) {
			for (Integer i = 0; i < subParents.size(); i++) {
				listOfSubParents.add(subParents.get(i));

				if (companyDAO.getSubParentsOfSubParents(subParents.get(i).getCommID().getCode()) != null) {
					listOfSubParents.addAll(getSubOfSub(subParents.get(i).getCommID().getCode()));
				}
			}
		}
		return listOfSubParents;
	}

	@Override
	@Transactional
	public String deleteParent(String code) {
		CompanyStructParent parent = getParent(code);
		List<CompanyStructSubparent> subs = parent.getSubParents();
		List<CompanyStructSubparent> subOfsub = new ArrayList<CompanyStructSubparent>();
		String isDeleted = "false";
		for (int i = 0; i < subs.size(); i++) {
			subOfsub.addAll(getSubOfSub(subs.get(i).getCommID().getCode()));
		}
		for (int i = 0; i < subOfsub.size(); i++) {
			isDeleted = companyDAO.deleteSubParent(subOfsub.get(i).getCommID().getCode());
		}
		isDeleted = companyDAO.deleteParent(code);
		return isDeleted;
	}

	@Override
	@Transactional
	public String deleteSubParent(String code) {
		CompanyStructSubparent sub = companyDAO.getSubParent(code);
		CompanyStructParent parent = sub.getParent();
		if (parent != null) {
			List<CompanyStructSubparent> subList = parent.getSubParents();
			subList.remove(sub);
		}
		String isDeleted = "false";
		List<CompanyStructSubparent> subParents = getSubOfSub(code);
		isDeleted = companyDAO.deleteSubParent(sub.getCommID().getCode());
		for (int i = 0; i < subParents.size(); i++) {
			isDeleted = companyDAO.deleteSubParent(subParents.get(i).getCommID().getCode());
		}
		return isDeleted;
	}

	@Override
	@Transactional
	public String deleteChild(String code) {
		String isDeleted = "false";
		CompanyStructChild child = companyDAO.getChild(code);
		CompanyStructSubparent sub = child.getSubParent();
		CompanyStructParent parent = child.getParent();
		List<CompanyStructChild> childrenList = new ArrayList<CompanyStructChild>();
		if (parent != null) {
			childrenList = parent.getChildren();
		} else {
			childrenList = sub.getChildren();
		}
		childrenList.remove(child);
		isDeleted = companyDAO.deleteChild(code);
		return isDeleted;
	}

	@Override
	@Transactional
	public void delmitParent(String code, String endDate) throws ParseException {
		Date enddate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
		CompanyStructParent parent = companyDAO.getParent(code);
		parent.getCommID().setEndDate(enddate);
		parent.getCommID().setDeleted(1);
		
	}

	@Override
	@Transactional
	public void delmitSubParent(String code, String endDate) throws ParseException {
		Date enddate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
		CompanyStructSubparent sub = companyDAO.getSubParent(code);
		sub.getCommID().setEndDate(enddate);
		sub.getCommID().setDeleted(1);
	}

	@Override
	@Transactional
	public void delmitChild(String code, String endDate) throws ParseException {
		Date enddate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
		CompanyStructChild child = companyDAO.getChild(code);
		child.getCommID().setEndDate(enddate);
		child.getCommID().setDeleted(1);
	}

	@Override
	@Transactional
	public void copyCompanyStructure(CompanyStructModel companyStructModel, String todayDate) throws ParseException {
		CompanyStructModel newModel = new CompanyStructModel();
		
		newModel.setCode(companyStructModel.getCode());
		newModel.setEndDate(companyStructModel.getEndDate());
		newModel.setStartDate(companyStructModel.getStartDate());
		newModel.setName(companyStructModel.getName());
		newModel.setHasParent(companyStructModel.getHasParent());
		newModel.setHasChild(companyStructModel.getHasChild());
		if (companyStructModel.getHasParent() == false) {
			newModel.setParentCode(null);
			delmitParent(companyStructModel.getCode(), todayDate);
		} else {
			newModel.setParentCode(companyStructModel.getParentCode());
		}
		processTheIncommingModel(newModel);

	}

}
