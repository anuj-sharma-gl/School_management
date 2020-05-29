package com.management.school.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.library.core.bean.SuccessResponseBean;
import com.library.core.exception.CustomExceptionHandler;
import com.management.school.employee.bean.ErrorResponse;
import com.management.school.employee.bean.UserDto;
import com.management.school.employee.bean.UserReqBean;
import com.management.school.employee.bean.enums.EmpType;
import com.management.school.employee.repository.UserService;

@RestControllerAdvice
@RestController
public class EmpController extends CustomExceptionHandler {
	@Autowired
	private Environment environment;
	@Autowired
	private UserService userService;

	@GetMapping(path = "/healthcheck")
	public String checkPort() {
		return "Employee-Service API working fine on port " + environment.getProperty("server.port");
	}

	@PostMapping(path = "/register")
	public ResponseEntity<SuccessResponseBean> saveUser(@RequestBody UserReqBean reqBean) {
		UserDto returnObj = userService.createUser(reqBean);
		SuccessResponseBean bean = new SuccessResponseBean(HttpStatus.CREATED.value(), "User registered succesfully",
				returnObj);
		ResponseEntity<SuccessResponseBean> response = new ResponseEntity<SuccessResponseBean>(bean,
				HttpStatus.CREATED);
		return response;
	}

	@GetMapping(path = "/")
	public ResponseEntity<SuccessResponseBean> getEmployeeList(
			@RequestParam(name = "type", required = false, defaultValue = "") String empType,
			@RequestParam(name = "page", required = true, defaultValue = "1") String page) {
		System.out.println("EmpType>> " + empType);
		System.out.println("Page>> " + page);
		EmpType employeeType = null;
		if (!String.valueOf(empType).isEmpty()) {
			switch (empType) {
			case "TEACHER":
				employeeType = EmpType.TEACHER;
				break;
			case "PEON":
				employeeType = EmpType.PEON;
				break;
			case "ACCOUNTANT":
				employeeType = EmpType.ACCOUNTANT;
				break;
			case "DRIVER":
				employeeType = EmpType.DRIVER;
				break;
			default:
				employeeType = null;
				break;
			}
		}
		List<UserDto> empList = userService.getUserList(employeeType, Integer.valueOf(page));
		SuccessResponseBean bean = new SuccessResponseBean(HttpStatus.OK.value(), "Successful", empList);
		ResponseEntity<SuccessResponseBean> response = new ResponseEntity<SuccessResponseBean>(bean, HttpStatus.OK);
		return response;
	}

	@GetMapping(path = "/userId/{id}")
	public ResponseEntity<SuccessResponseBean> getEmployeeById(@PathVariable String id) {
		UserDto returnObj = userService.getEmpById(id);
		SuccessResponseBean bean = new SuccessResponseBean(HttpStatus.OK.value(), "Succesfull", returnObj);
		ResponseEntity<SuccessResponseBean> response = new ResponseEntity<SuccessResponseBean>(bean,
				HttpStatus.OK);
		return response;
	}

	@PostMapping(path = "deleteEmployee/userId/{id}")
	public ResponseEntity<ErrorResponse> deleteEmployee(@PathVariable String id) {
		userService.deleteEmpById(id);

		ErrorResponse obj = new ErrorResponse();
		obj.setStatus(HttpStatus.OK.value());
		obj.setMessage("User deleted successfully");
		ResponseEntity<ErrorResponse> response = new ResponseEntity<ErrorResponse>(obj, HttpStatus.OK);
		return response;
	}

}
