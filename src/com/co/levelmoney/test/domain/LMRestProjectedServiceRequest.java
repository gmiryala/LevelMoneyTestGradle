package com.co.levelmoney.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LMRestProjectedServiceRequest {
int year;
int month;
LMRestServiceRequest args;
public int getYear() {
	return year;
}
public void setYear(int year) {
	this.year = year;
}
public int getMonth() {
	return month;
}
public void setMonth(int month) {
	this.month = month;
}
public LMRestServiceRequest getLmRestServiceRequest() {
	return args;
}
public void setLmRestServiceRequest(LMRestServiceRequest lmRestServiceRequest) {
	this.args = lmRestServiceRequest;
}

@Override
public String toString() { 
	return args.toString()+",\"year\":"+year+", \"month\":"+month;
}

}
