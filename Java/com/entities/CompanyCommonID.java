package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "companyCommonID")
public class CompanyCommonID {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name ="companyCommonID_id")
	private int id;
	
	@NotNull(message = "is required !")
	@Column(name="start_date")
	private Date startDate;
	
	@NotNull(message = "is required !")
	@Column(name="end_date")
	private Date endDate;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="code")
	private String code;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="name")
	private String name;
	
	@NotNull(message = "is required")
	@Column(name="delimited")
	private int deleted;
	
	public CompanyCommonID() {
	}
	
	public CompanyCommonID(Date startDate, Date endDate, String code, String name) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.code = code;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getDeleted() {
		return deleted;
	}
	
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

}
