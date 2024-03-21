package com.springboot.main.dto;

import com.springboot.main.model.Customer;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class PaymentDto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int paymentId;
	private String paymentType;
	private double paymentPrice;
	@OneToOne
	private Customer customer;
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public double getPaymentPrice() {
		return paymentPrice;
	}
	public void setPaymentPrice(double paymentPrice) {
		this.paymentPrice = paymentPrice;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", paymentType=" + paymentType + ", paymentPrice=" + paymentPrice
				+ ", customer=" + customer + "]";
	}
	
	

}
