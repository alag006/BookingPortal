package com.appengine.bookingportal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

//import java.util.Date;

@Entity
public class UserDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String userId;
	private Integer mobileNo;
	private String name;
	private String email;
	private List<AddressTemp> addressList = new ArrayList<AddressTemp>();
	//private Date dob;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Integer mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/*public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}*/
	
	public List<AddressTemp> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressTemp> addressList) {
		this.addressList = addressList;
	}
	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", mobileNo=" + mobileNo
				+ ", name=" + name + ", email=" + email + ", addressList="
				+ addressList + "]";
	}
}
