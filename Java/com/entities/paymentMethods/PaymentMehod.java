package com.entities.paymentMethods;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "payment_method")
public class PaymentMehod {

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name ="paymentmethod_id")
	private int id;
	
	@NotNull(message = "is required !")
	@Column(name="start_date")
	private Date startDate;
	
	@NotNull(message = "is required !")
	@Column(name="end_date")
	private Date endDate;
	
	@NotNull(message = "is required !")
	@Size(min = 1 , max = 45, message = "is required !")
	@Column(name="payment_method")
	private String paymentMethod;
	
	
	public PaymentMehod() {
	}

	

	public PaymentMehod(Date startDate, Date endDate,String paymentMethod) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.paymentMethod = paymentMethod;
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


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	

}
