package com.co.levelmoney.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

@JsonProperty("transaction-id")
String transaction_id;

@JsonProperty("account-id")
String account_id;

@JsonProperty("raw-merchant")
String raw_merchant;

String merchant;

@JsonProperty("is-pending")
boolean is_pending; 

@JsonProperty("transaction-time")
String transaction_time;

long amount;

@JsonProperty("previous-transaction-id")
String previous_transaction_id;

String categorization;

@JsonProperty("memo-only-for-testing")
String memo_only_for_testing;

@JsonProperty("payee-name-only-for-testing")
String payee_name_only_for_testing;

@JsonProperty("clear-date")
long clear_date;

public String getTransaction_id() {
	return transaction_id;
}

public void setTransaction_id(String transaction_id) {
	this.transaction_id = transaction_id;
}

public String getAccount_id() {
	return account_id;
}

public void setAccount_id(String account_id) {
	this.account_id = account_id;
}

public String getRaw_merchant() {
	return raw_merchant;
}

public void setRaw_merchant(String raw_merchant) {
	this.raw_merchant = raw_merchant;
}

public String getMerchant() {
	return merchant;
}

public void setMerchant(String merchant) {
	this.merchant = merchant;
}

public boolean isIs_pending() {
	return is_pending;
}

public void setIs_pending(boolean is_pending) {
	this.is_pending = is_pending;
}

public String getTransaction_time() {
	return transaction_time;
}

public void setTransaction_time(String transaction_time) {
	this.transaction_time = transaction_time;
}

public long getAmount() {
	return amount;
}

public void setAmount(long amount) {
	this.amount = amount;
}

public String getPrevious_transaction_id() {
	return previous_transaction_id;
}

public void setPrevious_transaction_id(String previous_transaction_id) {
	this.previous_transaction_id = previous_transaction_id;
}

public String getCategorization() {
	return categorization;
}

public void setCategorization(String categorization) {
	this.categorization = categorization;
}

public String getMemo_only_for_testing() {
	return memo_only_for_testing;
}

public void setMemo_only_for_testing(String memo_only_for_testing) {
	this.memo_only_for_testing = memo_only_for_testing;
}

public String getPayee_name_only_for_testing() {
	return payee_name_only_for_testing;
}

public void setPayee_name_only_for_testing(String payee_name_only_for_testing) {
	this.payee_name_only_for_testing = payee_name_only_for_testing;
}

public long getClear_date() {
	return clear_date;
}

public void setClear_date(long clear_date) {
	this.clear_date = clear_date;
}



}
