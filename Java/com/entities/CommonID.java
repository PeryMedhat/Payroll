package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "commonID")
public class CommonID {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name ="commonID_id")
	private int id;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@DateTimeFormat(pattern = "yyyy/mm/dd")
	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Date startDate;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@DateTimeFormat(pattern = "yyyy/mm/dd")
	@Temporal(TemporalType.DATE)
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
	
	public CommonID() {
		
	}
	
	public CommonID(
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") Date startDate,
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") Date endDate,
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") String code,
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") String name) {
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

}
