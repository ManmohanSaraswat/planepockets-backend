package com.planepockets.proton.exception;

public class AuthenticationException {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AuthenticationException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "AuthenticationException [message=" + message + "]";
	}
}
