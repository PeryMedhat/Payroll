package com.service.payType;

import java.text.ParseException;

import com.models.payType.PayTypeModel;

public interface PayTypeService {
	
	public void addPayType(PayTypeModel payTypeModel) throws ParseException;

}
