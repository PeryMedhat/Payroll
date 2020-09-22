package com.entities.banks;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "banks")
public class Banks {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name ="bank_id")
	private int id;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="code")
	private String code;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="name")
	private String name;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="country")
	private String country;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="branch")
	private String branch;
	
	public Banks() {
	}

	public Banks(int id,
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") String code,
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") String name,
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") String country,
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") String branch) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.country = country;
		this.branch = branch;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

		


}
