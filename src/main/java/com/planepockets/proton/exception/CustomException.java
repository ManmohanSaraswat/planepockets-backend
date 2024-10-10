package com.planepockets.proton.exception;

public class CustomException extends Exception {
	private static final long serialVersionUID = 1L;
	private String msg;

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return this.msg;
	}

	public CustomException(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "CustomException [msg=" + msg + "]";
	}

}
