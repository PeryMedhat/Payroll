package com.entities.gradingandsalary;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="grading_salary_structure")
public class GradingAndSalary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="grade")
	private String grade;
	
	@Column(name="level")
	private String level;
	
	@Column(name="min")
	private float min;
	
	@Column(name="mid")
	private float mid;
	
	@Column(name="max")
	private float max;
	
	@Column(name="basic_salary")
	private float basicSalary;
	
	public GradingAndSalary() {
		
	}

	public GradingAndSalary(Date startDate, Date endDate, String grade, String level, float min, float mid, float max,
			float basicSalary) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.grade = grade;
		this.level = level;
		this.min = min;
		this.mid = mid;
		this.max = max;
		this.basicSalary = basicSalary;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMid() {
		return mid;
	}

	public void setMid(float mid) {
		this.mid = mid;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(float basicSalary) {
		this.basicSalary = basicSalary;
	}
	

}
