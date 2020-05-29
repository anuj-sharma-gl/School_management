package com.management.school.employee.repository;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.management.school.employee.bean.UserDto;
import com.management.school.employee.bean.UserReqBean;
import com.management.school.employee.bean.enums.EmpType;

public interface UserService extends UserDetailsService {

	UserDto createUser(UserReqBean reqBean);

	UserDto getUserDetailByEmail(String email);

	List<UserDto> getUserList(EmpType empType, int page);

	UserDto getEmpById(String empId);
	
	void deleteEmpById(String empId);

}
