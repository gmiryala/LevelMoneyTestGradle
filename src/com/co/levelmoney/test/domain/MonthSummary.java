package com.co.levelmoney.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthSummary {
	String month;
	Summary summary;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Summary getSummary() {
		return summary;
	}
	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	@Override
    public String toString() { 
		return  "\""+month+"\":"+summary;
	}
	
		
}
