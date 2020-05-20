package com.service.gradingandsalary;

import java.text.ParseException;

import com.models.gradingandsalary.GradingAndSalaryModel;

public interface GradingAndSalaryService {
	public void addGradingAndSalary(GradingAndSalaryModel GradingAndSalaryModel) throws ParseException;
	public GradingAndSalaryModel getGradingAndSalary(String code);
	public void deleteGradingAndSalary(String code);
	public void delimitGradingAndSalary(String code, String endDate) throws ParseException;
	public void updateGradingAndSalaryData(GradingAndSalaryModel GradingAndSalaryModel) throws ParseException;
}
