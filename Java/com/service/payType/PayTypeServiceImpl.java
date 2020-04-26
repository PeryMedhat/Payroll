package com.service.payType;

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
		PayType payTypeObj = new PayType();
				
		InputValue inputValue = inputValueDAO.getIntervalByName(payTypeModel.getInputValue());
		Type type = typeDAO.getIntervalByName(payTypeModel.getType());
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
	
	
}
