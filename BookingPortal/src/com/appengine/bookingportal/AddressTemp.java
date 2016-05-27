package com.appengine.bookingportal;

public class AddressTemp {
	
	
	private String line1;
	private String line2;
	private Integer pinCode;
	private String state;

	public String getLine1() {
		return line1;
	}


	public void setLine1(String line1) {
		this.line1 = line1;
	}


	public String getLine2() {
		return line2;
	}


	public void setLine2(String line2) {
		this.line2 = line2;
	}


	public Integer getPinCode() {
		return pinCode;
	}


	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	@Override
	public String toString() {
		return "AddressTemp [line1=" + line1 + ", line2=" + line2
				+ ", pinCode=" + pinCode + ", state=" + state + "]";
	}
	
}
