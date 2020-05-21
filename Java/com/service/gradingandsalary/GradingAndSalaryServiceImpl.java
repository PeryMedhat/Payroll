package com.service.gradingandsalary;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.gradingandsalary.GradingAndSalaryDAO;
import com.entities.gradingandsalary.GradingAndSalary;
import com.models.gradingandsalary.GradingAndSalaryModel;
import com.rest.errorhandling.NotFoundException;
import com.rest.errorhandling.UniqunessException;

@Service
public class GradingAndSalaryServiceImpl implements GradingAndSalaryService{
	
	@Autowired
	private GradingAndSalaryDAO GradingAndSalaryDAO;
	
	
	@Override
	@Transactional
	public void addGradingAndSalary(GradingAndSalaryModel GradingAndSalaryModel) throws ParseException {
		
		Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(GradingAndSalaryModel.getStartDate());
		Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(GradingAndSalaryModel.getEndDate());
		float min = Float.parseFloat(GradingAndSalaryModel.getMin());
		float max = Float.parseFloat(GradingAndSalaryModel.getMax());
		float mid = Float.parseFloat(GradingAndSalaryModel.getMid());

		GradingAndSalary GradingAndSalaryObj = new GradingAndSalary();
		GradingAndSalaryObj.setDelimited(0);
		
		GradingAndSalaryObj.setStartDate(startDate);
		GradingAndSalaryObj.setEndDate(endDate);
		GradingAndSalaryObj.setGrade(GradingAndSalaryModel.getGrade());	
		GradingAndSalaryObj.setLevel(GradingAndSalaryModel.getLevel());
		GradingAndSalaryObj.setMin(min);
		GradingAndSalaryObj.setMax(max);
		GradingAndSalaryObj.setMid(mid);
		GradingAndSalaryObj.setBasicSalary(GradingAndSalaryModel.getBasicSalary());
		
		try {
			GradingAndSalaryDAO.addGradingAndSalary(GradingAndSalaryObj);
		}catch(Exception e) {
			e.printStackTrace();
			throw new UniqunessException("GradingAndSalary with grade:"+GradingAndSalaryModel.getGrade()+ " is already saved!");}
	}
	
	
	@Override
	@Transactional
	public GradingAndSalaryModel getGradingAndSalary(String grade) {
		try {
			GradingAndSalaryModel GradingAndSalaryModel= new GradingAndSalaryModel();
			GradingAndSalary GradingAndSalary = GradingAndSalaryDAO.getGradingAndSalary(grade);
			DateFormat dateFormat = new SimpleDateFormat();

			String startDate = dateFormat.format(GradingAndSalary.getStartDate());
			String endDate = dateFormat.format(GradingAndSalary.getEndDate());
			String max =Float.toString(GradingAndSalary.getMax());
			String mid =Float.toString(GradingAndSalary.getMid());
			String min =Float.toString(GradingAndSalary.getMin());
			
			GradingAndSalaryModel.setEndDate(endDate.substring(0, 6));
			GradingAndSalaryModel.setStartDate(startDate.substring(0, 6));
			GradingAndSalaryModel.setLevel(GradingAndSalary.getLevel());
			GradingAndSalaryModel.setGrade(grade);
			GradingAndSalaryModel.setBasicSalary(GradingAndSalary.getBasicSalary());
			GradingAndSalaryModel.setMax(max);
			GradingAndSalaryModel.setMid(mid);
			GradingAndSalaryModel.setMin(min);
			
			return GradingAndSalaryModel;
		}catch(Exception e) {
			e.printStackTrace();
			throw new NotFoundException("There is no GradingAndSalary with grade: "+grade+" exsits!");
		}
	}
	
	@Override
	@Transactional
	public void deleteGradingAndSalary(String grade) {
		try {
			GradingAndSalaryDAO.deleteGradingAndSalary(grade);	
		}catch(Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Cannot Delete! -the GradingAndSalary grade :"+grade+" is not saved");
		}
	}
	
	@Override
	@Transactional
	public void delimitGradingAndSalary(String grade, String endDate) throws ParseException {
		Date enddate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
		GradingAndSalary GradingAndSalary;
		try {
			GradingAndSalary =GradingAndSalaryDAO.getGradingAndSalary(grade);
		}catch(Exception e) {
			throw new NotFoundException("Cannot delimit GradingAndSalary with code:"+grade+" not found!");
		}	
		
		GradingAndSalary.setEndDate(enddate);
		GradingAndSalary.setDelimited(1);
	}
	
	@Override
	@Transactional
	public void updateGradingAndSalaryData(GradingAndSalaryModel GradingAndSalaryModel) throws ParseException {
		if(GradingAndSalaryModel.getGrade()!=null	&& GradingAndSalaryModel.getEndDate() !=null	&& GradingAndSalaryModel.getStartDate() != null
			&& GradingAndSalaryModel.getLevel() !=null && GradingAndSalaryModel.getMin() !=null&&
			GradingAndSalaryModel.getMid()!=null && GradingAndSalaryModel.getMax()!=null 
			&&GradingAndSalaryModel.getBasicSalary()!=null ) {
			
			Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(GradingAndSalaryModel.getStartDate());
			Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(GradingAndSalaryModel.getEndDate());
			String grade = GradingAndSalaryModel.getGrade();
			String level = GradingAndSalaryModel.getLevel();
			
			float min = Float.parseFloat(GradingAndSalaryModel.getMin());
			float max = Float.parseFloat(GradingAndSalaryModel.getMax());
			float mid = Float.parseFloat(GradingAndSalaryModel.getMid());

				
			GradingAndSalary GradingAndSalary = GradingAndSalaryDAO.getGradingAndSalary(grade);
			
			//Update the data of this GradingAndSalary 
			GradingAndSalary.setLevel(level);
			GradingAndSalary.setStartDate(startDate);
			GradingAndSalary.setEndDate(endDate);
			GradingAndSalary.setBasicSalary(GradingAndSalaryModel.getBasicSalary());
			GradingAndSalary.setMin(min);
			GradingAndSalary.setMid(mid);
			GradingAndSalary.setMax(max);
			GradingAndSalaryDAO.addGradingAndSalary(GradingAndSalary);
		}
		
	}
	
}
	
