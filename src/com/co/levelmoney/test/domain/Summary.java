package com.co.levelmoney.test.domain;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Summary {

	long spent;
	
	long income;


	public long getSpent() {
		return spent;
	}

	public void setSpent(long spent) {
		this.spent = spent;
	}

	public long getIncome() {
		return income;
	}

	public void setIncome(long income) {
		this.income = income;
	}
	
	@Override
    public String toString() { 
		return  "{\"spent:\"$" +RoundTo2Decimals(spent)*-1 +  "\",\"income\":\"$" +RoundTo2Decimals(income)+ "\"}";
	}
	
	private double RoundTo2Decimals(long val) {
		double d = val;
		d = d/10000;
        DecimalFormat df2 = new DecimalFormat("###.##");
    return Double.valueOf(df2.format(d));
}

	public void addSpent(long amount) {
		spent = spent + amount;
		
	}
	
	public void addIncome(long amount) {
		income = income + amount;
		
	}
	
}
