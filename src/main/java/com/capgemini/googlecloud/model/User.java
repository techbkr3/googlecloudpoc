package com.capgemini.googlecloud.model;

public class User {
	public String getAuthResult() {
		return authResult;
	}
	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}
	public String getUserName() {
		return userName;
	}
	//checking for push modified file in git repository
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String userName;
	private String password;
	private String authResult;

}
