package com.models;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class EmployeeModel {
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@DateTimeFormat(pattern = "yyyy/mm/dd")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@DateTimeFormat(pattern = "yyyy/mm/dd")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	private String code;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	private String name;
	
	private Boolean hasParent = false;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	private String parentCode;
	
	private Boolean hasChild = false;
	
	private EmployeeModel childModel;
	
	public EmployeeModel() {
		
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getHasParent() {
		return hasParent;
	}

	public void setHasParent(Boolean hasParent) {
		this.hasParent = hasParent;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}

	public EmployeeModel getChildModel() {
		return childModel;
	}

	public void setChildModel(EmployeeModel childModel) {
		this.childModel = childModel;
	}
	

}
