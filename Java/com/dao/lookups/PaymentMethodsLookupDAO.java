package com.dao.lookups;

import java.util.List;

import com.entities.lookups.PaymentMethodLookup;

public interface PaymentMethodsLookupDAO {
	
	public List<PaymentMethodLookup> getListOfPaymentMethodLookup();
	public PaymentMethodLookup getPaymentMethodByName(String name);
	public PaymentMethodLookup getPaymentMethodByCode(String code);	
	
}
