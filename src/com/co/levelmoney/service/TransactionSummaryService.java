package com.co.levelmoney.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.TreeMap;

import com.co.levelmoney.test.domain.Summary;
import com.co.levelmoney.test.domain.Transaction;

/**
 * This class has functions to summarize transactions
 * @author gmiryala
 *
 */
public class TransactionSummaryService {

	
	/**
	 * This calculates avrg summary by taking all Summary list
	 * @param values
	 * @return Summary
	 */
	private Summary getAverage(Collection<Summary> values) {
		Summary avrgSummary = new Summary();
		OptionalDouble sumIncome = values.stream().mapToLong(Summary::getIncome).average();
		avrgSummary.setIncome(Math.round(sumIncome.getAsDouble()));
		
		OptionalDouble sumSpent = values.stream().mapToLong(Summary::getSpent).average();
		avrgSummary.setSpent(Math.round(sumSpent.getAsDouble()));
		
		return avrgSummary;
	}

	/**
	 * This method takes all transactions and provided Monthly Summary
	 * @param transactions
	 * @return Map of Month and Summary
	 */
	public Map<String, Summary> getMonthlySummaryMap(List<Transaction> transactions){
		
		Map<String, Summary> monthlyMap = new HashMap<>();
		for (Transaction transaction : transactions)
		{
			addTransactiontoMonthlySumMap(monthlyMap,transaction);
		}
		
		Map<String, Summary> sortedMap = new TreeMap<String, Summary>(monthlyMap);
		Summary avrgSummary = getAverage(sortedMap.values());
		sortedMap.put("average", avrgSummary);
		return sortedMap;
	}

	/**
	 * Add transaction to respective month Summary in Monthly Map
	 * @param monthlyMap
	 * @param transaction
	 */
	private void addTransactiontoMonthlySumMap(Map<String, Summary> monthlyMap, Transaction transaction) {
		String month = transaction.getTransaction_time().substring(0,7);
		Summary summary = getSummary(monthlyMap, month);
		if(transaction.getAmount()<0)
		{
			summary.addSpent(transaction.getAmount());
		}
		else
		{
			summary.addIncome(transaction.getAmount());			
		}
		
	}
	
	/**
	 * This method create new Summary for the month which is not in map
	 * @param monthlyMap
	 * @param month
	 * @return Summary
	 */
	private Summary getSummary(Map<String, Summary> monthlyMap, String month) {

		Summary summary = null;
		if(monthlyMap.containsKey(month))
		{
			summary = monthlyMap.get(month);
		} else
		{

			summary = new Summary();
			summary.setIncome(0);
			summary.setSpent(0);
			
			monthlyMap.put(month, summary);
		}
		return summary;
	}

		
}
