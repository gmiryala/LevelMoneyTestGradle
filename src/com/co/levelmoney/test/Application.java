package com.co.levelmoney.test;



import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.co.levelmoney.service.TransactionSummaryService;
import com.co.levelmoney.test.domain.LMRestProjectedServiceRequest;
import com.co.levelmoney.test.domain.LMRestServiceRequest;
import com.co.levelmoney.test.domain.LMRestTransactionsResponse;
import com.co.levelmoney.test.domain.Transaction;
import com.co.levelmoney.util.StringConstants;
import com.google.gson.Gson;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private static String[] arguments = null;
	public static void main(String args[]) {
		arguments = args;
		SpringApplication.run(Application.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
	 * This is to run and respond on CommandLineRunner
	 * @param restTemplate
	 * @return
	 * @throws Exception
	 */
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		
		TransactionSummaryService tss = new TransactionSummaryService();
		
		return args -> {	
			log.info(getJSONString(tss.getMonthlySummaryMap(getAllTransactions(restTemplate))));
		};


	}

	/**
	 * It calls GetAll Transactions rest service
	 * and returns restTransactions Response
	 * @param restTemplate
	 * @return LMRestTransactionsResponse
	 */
	private List<Transaction> getAllTransactions(RestTemplate restTemplate) {
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

		if(arguments.length >1 && "crystal-ball".equalsIgnoreCase(arguments[1]))
		{
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
		return allTransactions;
	
	}
	
	private String getJSONRequestString(String string) {
		return "{"+string+"}";
	}

	/**
	 * All transactions object will have merge of both
	 * @param allTransactions
	 * @param projectedTransactions
	 */
	private void mergeTransactions(List<Transaction> allTransactions, List<Transaction> projectedTransactions) {
		if(allTransactions == null)
		{
			allTransactions = new ArrayList<Transaction>();
		}
		
		if(projectedTransactions !=null &&  projectedTransactions.size() > 0)
		{
			projectedTransactions.remove(allTransactions);
			allTransactions.addAll(projectedTransactions);
		}

	}

	/**
	 * Returns JSOn String
	 * @param obj
	 * @return
	 */
	private String getJSONString(Object obj){
		Gson gson = new Gson(); 
		return gson.toJson(obj);
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
	
	
}