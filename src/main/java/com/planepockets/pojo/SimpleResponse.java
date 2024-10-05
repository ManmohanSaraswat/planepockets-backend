package com.planepockets.pojo;

public class SimpleResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SimpleResponse(String message) {
        this.message = message;
    }

    public SimpleResponse() {

    }
}
