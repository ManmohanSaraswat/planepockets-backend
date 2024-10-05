package com.planepockets.pojo;

public class B2bPojo {
    private String name;
    private String from;
    private String to;
    private String startDate;
    private String endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public B2bPojo(String name, String from, String to, String startDate, String endDate) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public B2bPojo() {
        
    }
}
