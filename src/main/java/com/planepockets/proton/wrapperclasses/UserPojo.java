package com.planepockets.proton.wrapperclasses;

public class UserPojo {
	private String loginId;
	private String fullName;
	private String contactNumber;
	private String jwtToken;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public UserPojo(String loginId, String fullName, String contactNumber, String jwtToken) {
		this.loginId = loginId;
		this.fullName = fullName;
		this.contactNumber = contactNumber;
		this.jwtToken = jwtToken;
	}
}
