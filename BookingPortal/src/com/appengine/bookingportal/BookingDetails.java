package com.appengine.bookingportal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookingDetails {
	
	@Id
	private Integer orderId;
	private String orderName;
	private String paymentType;
	private Integer orderValue;
	//private Address orderAddress;

	public Integer getOrderId() {
		return orderId;
	}



	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}



	public String getOrderName() {
		return orderName;
	}



	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}



	public String getPaymentType() {
		return paymentType;
	}



	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}



	public Integer getOrderValue() {
		return orderValue;
	}



	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	
	@Override
	public String toString() {
		return "BookingDetails [orderId=" + orderId + ", orderName="
				+ orderName + ", paymentType=" + paymentType + ", orderValue="
				+ orderValue + "]";
	}

	

}
