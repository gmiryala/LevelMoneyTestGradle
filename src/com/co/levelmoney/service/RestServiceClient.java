package com.co.levelmoney.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.co.levelmoney.test.Application;
import com.co.levelmoney.test.domain.LMRestProjectedServiceRequest;
import com.co.levelmoney.test.domain.LMRestServiceRequest;
import com.co.levelmoney.test.domain.LMRestTransactionsResponse;
import com.co.levelmoney.test.domain.Transaction;
import com.co.levelmoney.util.StringConstants;

public class RestServiceClient {
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	/**
	 * It calls GetAll Transactions rest service
	 * and returns restTransactions Response
	 * @param restTemplate
	 * @return LMRestTransactionsResponse
	 */
	public List<Transaction> getAllTransactions(RestTemplate restTemplate, String[] arguments) {
		LMRestServiceRequest serviceRequest = getServiceRequest();
		List<Transaction> allTransactions = null;
		List<Transaction> projectedTransactions = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(getJSONRequestString(serviceRequest.toString()),headers);
		
		LMRestTransactionsResponse allTransactionsResponse = 
				restTemplate.postForObject(StringConstants.getAllTransactionsURL, entity, LMRestTransactionsResponse.class);
		if("no-error".equalsIgnoreCase(allTransactionsResponse.getError()))
		{
			allTransactions = allTransactionsResponse.getTransactions();
		}

		if(Arrays.asList(arguments).contains("crystal-ball"))
		{
			log.info("Retriving projected transactions");
			LMRestProjectedServiceRequest lmRestProjectedServiceRequest = getProjectedServiceRequest();
			 
			entity = new HttpEntity<String>(getJSONRequestString(lmRestProjectedServiceRequest.toString()),headers);
			LMRestTransactionsResponse projectedTransResponse = 
					restTemplate.postForObject(StringConstants.GetProjectedTransactionsForMonth, entity, LMRestTransactionsResponse.class);
			if("no-error".equalsIgnoreCase(projectedTransResponse.getError()))
			{
				projectedTransactions = projectedTransResponse.getTransactions();
			}
		}
		mergeTransactions(allTransactions, projectedTransactions);
		if(Arrays.asList(arguments).contains("ignore-donuts"))
		{
			removeDonutTransactions(allTransactions);
		}
		return allTransactions;
	
	}

	/**
	 * Remove donut transactions
	 * @param allTransactions
	 */
	private void removeDonutTransactions(List<Transaction> allTransactions) {
		log.info("Total transactions before removing from Donut : "+allTransactions.size());
		List<Transaction> filteredTransactions = new ArrayList<Transaction>();
		allTransactions.stream().filter(p -> (p.getMerchant().indexOf("Donut") == -1 && p.getMerchant().indexOf("DUNKIN") == -1)).forEach(filteredTransactions::add);
		log.info("Total transactions after removing from Donut and DUNKIN: "+filteredTransactions.size());
		allTransactions = filteredTransactions;
	}


	/**
	 * This will create args for every rest webservice
	 * @return service request
	 */
	private LMRestServiceRequest getServiceRequest() {
		LMRestServiceRequest serviceRequest = new LMRestServiceRequest();
    	serviceRequest.setUid(StringConstants.uid);
    	serviceRequest.setToken(StringConstants.token);
    	serviceRequest.setApi(StringConstants.apiToken);
    	
		return serviceRequest;
	}
	
	/**
	 * This will create args for projected transactions rest webservice
	 * @return service request
	 */
	private LMRestProjectedServiceRequest getProjectedServiceRequest() {
		LMRestProjectedServiceRequest lmRestProjectedServiceRequest = new LMRestProjectedServiceRequest();
		lmRestProjectedServiceRequest.setLmRestServiceRequest(getServiceRequest());
		Calendar now = Calendar.getInstance();
		lmRestProjectedServiceRequest.setYear(now.get(Calendar.YEAR));
		lmRestProjectedServiceRequest.setMonth(now.get(Calendar.MONTH)+1);
    	
		return lmRestProjectedServiceRequest;
	}
	
	/**
	 * All transactions object will have merge of both
	 * @param allTransactions
	 * @param projectedTransactions
	 */
	private void mergeTransactions(List<Transaction> allTransactions, List<Transaction> projectedTransactions) {


		if(allTransactions == null)
		{
			log.info("allTransactions is null ");
			allTransactions = new ArrayList<Transaction>();
		}
		log.info("allTransactions is : "+allTransactions.size());
		
		if(projectedTransactions !=null &&  projectedTransactions.size() > 0)
		{
			log.info("projectedTransactions count: "+projectedTransactions.size());
			projectedTransactions.remove(allTransactions);
			log.info("projectedTransactions count after removing duplicates: "+projectedTransactions.size());
			allTransactions.addAll(projectedTransactions);
			log.info("allTransactions count after merging: "+allTransactions.size());
		}

	}


	/**
	 * 
	 * @param string
	 * @return
	 */
	private String getJSONRequestString(String string) {
		return "{"+string+"}";
	}
}
