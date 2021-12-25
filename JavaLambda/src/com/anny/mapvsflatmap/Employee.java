package com.anny.mapvsflatmap;

import java.util.List;

public class Employee {
	private long id;
	private String name;
	private String email;
	private List<String> phoneNumber;
	
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}


	public Employee(long id, String name, String email, List<String> phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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


	public List<String> getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
