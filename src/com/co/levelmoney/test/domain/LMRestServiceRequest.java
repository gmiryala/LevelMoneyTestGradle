package com.co.levelmoney.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LMRestServiceRequest {
	long uid;
	String token;
	@JsonProperty("api-token")
	String apiToken;

	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getApi() {
		return apiToken;
	}
	public void setApi(String api) {
		this.apiToken = api;
	}
	
	@Override
    public String toString() { 
		return "\"args\": {"+"\"uid\":" + String.valueOf(uid) + ",\"token\":\""+token+"\", \"api-token\":\""+apiToken + "\", \"json-strict-mode\": false, \"json-verbose-response\": false}";
	}
	
}
