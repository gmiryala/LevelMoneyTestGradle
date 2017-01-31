package com.co.levelmoney.test;



import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.co.levelmoney.service.RestServiceClient;
import com.co.levelmoney.service.TransactionSummaryService;
import com.co.levelmoney.test.domain.Summary;
import com.co.levelmoney.util.JSONUtil;

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
		
		RestServiceClient rsc = new RestServiceClient();		
		TransactionSummaryService tss = new TransactionSummaryService();
		JSONUtil jsonutil = new JSONUtil();
		
		Map<String, Summary> monthlyMap = 
				tss.getMonthlySummaryMap(rsc.getAllTransactions(restTemplate, arguments));
		
		String response = jsonutil.convertMaptoJSON(monthlyMap);
		return args -> {	
			log.info(response);
		};

	}
		
}