package com.library.core.bean;

import java.util.Date;

public class ExceptionResponseBean {
private int status;
private String error;
private String message;
private Date timestamp;

public ExceptionResponseBean(int status, String error, String message, Date timestamp) {
	super();
	this.status = status;
	this.error = error;
	this.message = message;
	this.timestamp = timestamp;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getError() {
	return error;
}
public void setError(String error) {
	this.error = error;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Date getTimestamp() {
	return timestamp;
}
public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
}


}
