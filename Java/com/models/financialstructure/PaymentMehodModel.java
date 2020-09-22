package com.models.financialstructure;

public class PaymentMehodModel {

	private String startDate;
	private String endDate;
	private String paymentMethod;
	
	
	public PaymentMehodModel() {
	}

	public PaymentMehodModel(String startDate, String endDate,String paymentMethod) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.paymentMethod = paymentMethod;
	}

	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
