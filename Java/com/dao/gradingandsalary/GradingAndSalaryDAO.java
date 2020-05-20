package com.dao.gradingandsalary;

import java.util.List;

import com.entities.gradingandsalary.GradingAndSalary;

public interface GradingAndSalaryDAO {
	public void addGradingAndSalary(GradingAndSalary GradingAndSalary);
	public GradingAndSalary getGradingAndSalary(String code);
	public GradingAndSalary getGradingAndSalaryById(int id);
	public void deleteGradingAndSalary(String code);
	public List<GradingAndSalary> getAllGradingAndSalarys();
}
