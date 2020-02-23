package com.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name ="emp_struct_child")
public class EmpStructChild {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="child_id")
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "commonID_id")
	private CommonID commID;
	

	@ManyToOne(cascade = {CascadeType.PERSIST,
			  CascadeType.MERGE,
			  CascadeType.REFRESH,
			  CascadeType.DETACH },
			  fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	private EmpStructParent parent;
	
	@ManyToOne(cascade = {CascadeType.PERSIST,
			  CascadeType.MERGE,
			  CascadeType.REFRESH,
			  CascadeType.DETACH },
			  fetch = FetchType.EAGER)
	@JoinColumn(name="subparent_id")
	private EmpStructSubparent subParent;
	
	public EmpStructChild() {
		// TODO Auto-generated constructor stub
	}
	
	
	public EmpStructChild(CommonID commID) {
		this.commID = commID;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CommonID getCommID() {
		return commID;
	}

	public void setCommID(CommonID commID) {
		this.commID = commID;
	}

	public EmpStructParent getParent() {
		return parent;
	}

	public void setParent(EmpStructParent parent) {
		this.parent = parent;
	}

	public EmpStructSubparent getSubParent() {
		return subParent;
	}

	public void setSubParent(EmpStructSubparent subParent) {
		this.subParent = subParent;
	}
	
}
