package com.management.school.employee.bean;

import com.management.school.employee.bean.enums.EmpType;

public class UserReqBean {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private EmpType empType;	// TEACHER, DRIVER
	private boolean isPermanent;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public EmpType getEmpType() {
		return empType;
	}
	public void setEmpType(EmpType empType) {
		this.empType = empType;
	}
	public boolean isPermanent() {
		return isPermanent;
	}
	public void setPermanent(boolean isPermanent) {
		this.isPermanent = isPermanent;
	}

	
}
