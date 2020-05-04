package com.service.payType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.lookups.InputValueLookupDAO;
import com.dao.lookups.IntervalLookupDAO;
import com.dao.lookups.TypeLookupDAO;
import com.dao.payType.PayTypeDAO;
import com.entities.lookups.InputValue;
import com.entities.lookups.Interval;
import com.entities.lookups.Type;
import com.entities.payType.PayType;
import com.entities.payType.PayTypeCommId;
import com.models.payType.PayTypeModel;
import com.rest.errorhandling.NotFoundException;
import com.rest.errorhandling.UniqunessException;

@Service
public class PayTypeServiceImpl implements PayTypeService{
	@Autowired
	private PayTypeDAO payTypeDAO;
	
	@Autowired
	private InputValueLookupDAO inputValueDAO;
	
	@Autowired 
	private IntervalLookupDAO intervalDAO;
	
	@Autowired
	private TypeLookupDAO typeDAO;
	
	@Override
	@Transactional
	public void addPayType(PayTypeModel payTypeModel) throws ParseException {
		
		Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(payTypeModel.getStartDate());
		Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(payTypeModel.getEndDate());
		
		PayTypeCommId commId=new PayTypeCommId(startDate,endDate,payTypeModel.getCode(),payTypeModel.getName());
		commId.setDeleted(0);
		PayType payTypeObj = new PayType();
				
		InputValue inputValue = inputValueDAO.getInputValueByName(payTypeModel.getInputValue());
		Type type = typeDAO.getTypeByName(payTypeModel.getType());
		Interval interval =intervalDAO.getIntervalByName(payTypeModel.getInterval());
		
		payTypeObj.setCommID(commId);
		payTypeObj.setCostCenter(null);
		payTypeObj.setGlAssignemnt(null);
		payTypeObj.setTaxes(null);
		payTypeObj.setInputvalue(inputValue.getCode());
		payTypeObj.setInterval(interval.getCode());
		payTypeObj.setType(type.getCode());
		try {
			payTypeDAO.addPayType(payTypeObj);
		}catch(Exception e) {throw new UniqunessException("payType with code:"+payTypeModel.getCode()+ " is already saved!");}
	}
	
	
	@Override
	@Transactional
	public PayTypeModel getPayType(String code) {
		try {
			PayTypeModel payTypeModel= new PayTypeModel();
			PayType payType = payTypeDAO.getPayType(code);
			DateFormat dateFormat = new SimpleDateFormat();

			InputValue inputValue = inputValueDAO.getInputValueByCode(payType.getInputvalue());
			Type type = typeDAO.getTypeByCode(payType.getType());
			Interval interval =intervalDAO.getIntervalByCode(payType.getInterval());
			
			
			String startDate = dateFormat.format(payType.getCommID().getStartDate());
			String endDate = dateFormat.format(payType.getCommID().getEndDate());
			
			payTypeModel.setCode(code);
			payTypeModel.setEndDate(endDate.substring(0, 6));
			payTypeModel.setStartDate(startDate.substring(0, 6));
			payTypeModel.setName(payType.getCommID().getName());
			payTypeModel.setInputValue(inputValue.getName());
			payTypeModel.setInterval(interval.getName());
			payTypeModel.setType(type.getName());
			return payTypeModel;
		}catch(Exception e) {
			e.printStackTrace();
			throw new NotFoundException("There is no payType with code: "+code+" exsits!");
		}
	}
	
	@Override
	@Transactional
	public void deletePayType(String code) {
		try {
			payTypeDAO.deletePayType(code);	
		}catch(Exception e) {
			throw new NotFoundException("Cannot Delete! -the PayType code :"+code+" is not saved");
		}
	}
	
	@Override
	@Transactional
	public void delimitPayType(String code, String endDate) throws ParseException {
		Date enddate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
		PayType payType;
		try {
			payType =payTypeDAO.getPayType(code);
		}catch(Exception e) {
			throw new NotFoundException("Cannot delimit PayType with code:"+code+" not found!");
		}	
		
		payType.getCommID().setEndDate(enddate);
		payType.getCommID().setDeleted(1);
	}
	
	@Override
	@Transactional
	public void updatePayTypeData(PayTypeModel payTypeModel) throws ParseException {
		if(payTypeModel.getCode()!=null	&& payTypeModel.getEndDate() !=null	&& payTypeModel.getStartDate() != null
			&& payTypeModel.getName() !=null && payTypeModel.getInputValue() !=null	&& payTypeModel.getInterval() !=null
			&& payTypeModel.getType() !=null) {
			Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(payTypeModel.getStartDate());
			Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(payTypeModel.getEndDate());
			String code = payTypeModel.getCode();
			PayType payType = payTypeDAO.getPayType(code);
			payType.getCommID().setName(payTypeModel.getName());
			payType.getCommID().setStartDate(startDate);
			payType.getCommID().setEndDate(endDate);
			payType.setInputvalue(payTypeModel.getInputValue());
			payType.setType(payTypeModel.getType());
			payType.setInterval(payTypeModel.getInterval());
			
			payTypeDAO.addPayType(payType);
		
		}
	}
	
	
	@Override
	@Transactional
	public void copyPayType(PayTypeModel payTypeModel, String todayDate) throws Exception {
		
		
	}
	
}
