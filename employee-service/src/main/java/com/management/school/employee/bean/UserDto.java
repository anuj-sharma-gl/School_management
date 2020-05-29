package com.management.school.employee.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.management.school.employee.bean.enums.EmpType;

public class UserDto {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
//	@JsonInclude(Include.NON_NULL)
//	private String password;
	private EmpType empType;	// TEACHER, DRIVER
	private boolean isPermanent;
	@JsonIgnore
	private int superAdmin;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public int getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(int superAdmin) {
		this.superAdmin = superAdmin;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
	

}
