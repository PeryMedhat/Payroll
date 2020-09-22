package com.entities.costCenterStructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "cost_center_structure")
public class CostCenterStructure {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name ="cost_center_structure_id")
	private int id;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="code")
	private String code;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="name")
	private String name;
	
	public CostCenterStructure() {
	}

	
	public CostCenterStructure(
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") String code,
			@NotNull(message = "is required !") @Size(min = 1, max = 45, message = "is required !") String name) {
		this.code = code;
		this.name = name;
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

	

}
